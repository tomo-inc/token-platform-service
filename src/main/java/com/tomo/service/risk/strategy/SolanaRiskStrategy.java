package com.tomo.service.risk.strategy;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tomo.feign.GoplusClient;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.ChainPlatformType;
import com.tomo.model.RiskLevel;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.model.resp.SolanaSecurityResp;
import com.tomo.service.market.MarketTokenSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class SolanaRiskStrategy extends AbstractRiskStrategy{
    final GoplusClient goplusClient;
    final MarketTokenInfoMapper tokenMapper;
    final MarketTokenSecurityService tokenSecurityInfoService;

    @Override
    public ChainPlatformType getType() {
        return ChainPlatformType.SOLANA;
    }

    public static RiskLevel getRiskLevel(SolanaSecurityResp.SolanaTokenDetails tokenSecurity){
        var caseCount = 0;
        if(tokenSecurity.getBalance_mutable_authority().getStatus().equals("1")){
            caseCount++;
        }
        if(!tokenSecurity.getTransfer_hook().isEmpty()){
            caseCount++;
        }
        if(tokenSecurity.getCreators() != null && !tokenSecurity.getCreators().isEmpty()){
            boolean hasMaliciousAddress = tokenSecurity.getCreators().stream()
                    .anyMatch(creator -> "1".equals(creator.getMalicious_address()));
            if (hasMaliciousAddress) {
                caseCount++;
            }
        }
        var transferFee = tokenSecurity.getTransfer_fee();
        BigDecimal feeRate = null;
        if(transferFee != null && transferFee.getCurrent_fee_rate() != null){
            feeRate = transferFee.getCurrent_fee_rate().getFee_rate().divide(BigDecimal.valueOf(1000), RoundingMode.HALF_UP);
        }
        if(caseCount>=2 || tokenSecurity.getNon_transferable().equals("1") || (feeRate!=null && feeRate.compareTo(BigDecimal.valueOf(0.5)) > 0)){
            return RiskLevel.RISK;
        }
        caseCount = 0;
        if(tokenSecurity.getClosable().getStatus().equals("1")){
            caseCount++;
        }
        if(tokenSecurity.getFreezable().getStatus().equals("1")){
            caseCount++;
        }
        if(tokenSecurity.getTransfer_fee_upgradable() != null && tokenSecurity.getTransfer_fee_upgradable().getStatus().equals("1")){
            caseCount++;
        }
        if(caseCount >= 3 || tokenSecurity.getTransfer_fee_upgradable().getStatus().equals("1") ||
                (feeRate!=null && feeRate.compareTo(BigDecimal.valueOf(0.5)) < 0 &&
                        feeRate.compareTo(BigDecimal.valueOf(0.1)) >= 0)){
            return RiskLevel.WARN;
        }
        return RiskLevel.SAFE;
    }

    @Override
    public List<MarketTokenSecurityInfo> syncRisk(String contractAddresses, Long chainIndex, String token){
        var solanaResponse = goplusClient.getSolanaSecurity(contractAddresses,token);
        List<MarketTokenSecurityInfo> tokenSecurityInfoDOList= new ArrayList<>();
        if(solanaResponse.getCode() != 1) return null;
        var results = solanaResponse.getResult();
        Set<String> keys = results.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        findMissingAddresses(contractAddresses, keys, chainIndex, tokenMapper);
        Set<Map.Entry<String, SolanaSecurityResp.SolanaTokenDetails>> entries = results.entrySet();

        for (Map.Entry<String, SolanaSecurityResp.SolanaTokenDetails> entry :results.entrySet()) {
            String key = entry.getKey().toLowerCase();
            try {
                SolanaSecurityResp.SolanaTokenDetails tokenSecurity = entry.getValue();
                LambdaQueryWrapper<MarketTokenSecurityInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(MarketTokenSecurityInfo::getChainIndex, chainIndex);
                lambdaQueryWrapper.eq(MarketTokenSecurityInfo::getAddress, key);
                MarketTokenSecurityInfo tokenSecurityDO = tokenSecurityInfoService.getOne(lambdaQueryWrapper);
                if(tokenSecurityDO == null){
                    tokenSecurityDO = new MarketTokenSecurityInfo();
                }
                tokenSecurityDO.setChainIndex(chainIndex);
                tokenSecurityDO.setAddress(key);
                tokenSecurityDO.setDefaultAccountState(Integer.parseInt(tokenSecurity.getDefault_account_state()));
                tokenSecurityDO.setTrustedToken(tokenSecurity.getTrusted_token() != 0);
                tokenSecurityDO.setTotalSupply(tokenSecurity.getTotal_supply());
                if (tokenSecurity.getBalance_mutable_authority() != null) {
                    tokenSecurityDO.setBalanceMutableAuthority(convertToJson(tokenSecurity.getBalance_mutable_authority()));
                }
                if (tokenSecurity.getClosable() != null) {
                    tokenSecurityDO.setClosable(convertToJson(tokenSecurity.getClosable()));
                }
                if(tokenSecurity.getMintable() != null){
                    tokenSecurityDO.setMintable(convertToJson(tokenSecurity.getMintable()));
                }
                if (tokenSecurity.getCreators() != null) {
                    tokenSecurityDO.setCreator(convertToJson(tokenSecurity.getCreators()));
                }
                if (tokenSecurity.getDefault_account_state_upgradable() != null) {
                    tokenSecurityDO.setDefaultAccountStateUpgradable(convertToJson(tokenSecurity.getDefault_account_state_upgradable()));
                }
                if (tokenSecurity.getDex() != null) {
                    tokenSecurityDO.setDex(convertToJson(tokenSecurity.getDex()));
                }
                if (tokenSecurity.getFreezable() != null) {
                    tokenSecurityDO.setFreezable(convertToJson(tokenSecurity.getFreezable()));
                }
                if (tokenSecurity.getHolders() != null) {
                    tokenSecurityDO.setHolders(convertToJson(tokenSecurity.getHolders()));
                }
                if (tokenSecurity.getLp_holders() != null) {
                    tokenSecurityDO.setLpHolders(convertToJson(tokenSecurity.getLp_holders()));
                }
                if (tokenSecurity.getMetadata() != null) {
                    tokenSecurityDO.setMetadata(convertToJson(tokenSecurity.getMetadata()));
                }
                if (tokenSecurity.getMetadata_mutable() != null) {
                    tokenSecurityDO.setMetadataMutable(convertToJson(tokenSecurity.getMetadata_mutable()));
                }
                if (tokenSecurity.getNon_transferable() != null) {
                    tokenSecurityDO.setNonTransferable("1".equals(tokenSecurity.getNon_transferable()));
                }
                if (tokenSecurity.getTransfer_fee() != null && tokenSecurity.getTransfer_fee().getCurrent_fee_rate() != null) {
                    tokenSecurityDO.setTransferFee(convertToJson(tokenSecurity.getTransfer_fee()));
                }
                if (tokenSecurity.getTransfer_fee_upgradable() != null) {
                    tokenSecurityDO.setTransferFeeUpgradable(convertToJson(tokenSecurity.getTransfer_fee_upgradable()));
                }
                if(tokenSecurity.getTransfer_hook_upgradable() !=null){
                    tokenSecurityDO.setTransferHookUpgradable(convertToJson(tokenSecurity.getTransfer_hook_upgradable()));
                }

                tokenSecurityUpsert(chainIndex, key, tokenSecurityDO, tokenSecurityInfoService, getRiskLevel(tokenSecurity), tokenMapper);
                tokenSecurityInfoDOList.add(tokenSecurityDO);
            } catch (Exception e) {
                log.error("Sync sol Risk {} {} error:", chainIndex, key, e);
            }
        }
        return tokenSecurityInfoDOList;
    }
}
