package com.tomo.service.risk.strategy;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.ChainEnum;
import com.tomo.model.ChainPlatformType;
import com.tomo.model.RiskLevel;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.service.market.MarketTokenSecurityService;
import io.gopluslabs.client.GoPlusClient;
import io.gopluslabs.client.model.ResponseWrapperTokenSecurityResult;
import io.gopluslabs.client.request.TokenSecurityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class EthereumRiskStrategy extends AbstractRiskStrategy {
    final MarketTokenInfoMapper tokenMapper;
    final MarketTokenSecurityService tokenSecurityInfoService;

    @Override
    public ChainPlatformType getType() {
        return ChainPlatformType.EVM;
    }

    public static RiskLevel getRiskLevel(MarketTokenSecurityInfo tokenSecurityDO) {
        var caseCount = 0;
        if (tokenSecurityDO.getOwnerChangeBalance()) {
            caseCount++;
        }
        if (tokenSecurityDO.getExternalCall()) {
            caseCount++;
        }
        if (tokenSecurityDO.getHiddenOwner()) {
            caseCount++;
        }
        if (tokenSecurityDO.getIsProxy()) {
            caseCount++;
        }
        if (!tokenSecurityDO.getIsOpenSource()) {
            caseCount++;
        }
        if (caseCount >= 2 || tokenSecurityDO.getIsHoneypot()
                || (tokenSecurityDO.getBuyTax() !=null && tokenSecurityDO.getBuyTax().compareTo(BigDecimal.valueOf(0.5)) > 0)
                || (tokenSecurityDO.getSellTax() !=null && tokenSecurityDO.getSellTax().compareTo(BigDecimal.valueOf(0.5)) > 0)
                || !tokenSecurityDO.getIsTrueToken()) {
            return RiskLevel.RISK;
        }
        caseCount = 0;
        if(tokenSecurityDO.getTransferPausable()){
            caseCount++;
        }
        if(tokenSecurityDO.getTradingCooldown()){
            caseCount++;
        }
        if(tokenSecurityDO.getIsBlacklisted()){
            caseCount++;
        }
        if(tokenSecurityDO.getIsWhitelisted()){
            caseCount++;
        }
        if(caseCount >= 3 || tokenSecurityDO.getBuyTax() == null || tokenSecurityDO.getSellTax() == null
                || tokenSecurityDO.getBuyTax().compareTo(BigDecimal.valueOf(0.1)) >= 0 && tokenSecurityDO.getBuyTax().compareTo(BigDecimal.valueOf(0.5)) < 0
                || tokenSecurityDO.getSellTax().compareTo(BigDecimal.valueOf(0.1)) >= 0 && tokenSecurityDO.getSellTax().compareTo(BigDecimal.valueOf(0.5)) < 0
                || tokenSecurityDO.getCannotSellAll() || tokenSecurityDO.getSlippageModifiable() || tokenSecurityDO.getPersonalSlippageModifiable()){
            return RiskLevel.WARN;
        }
        return RiskLevel.SAFE;
    }

    @Override
    public List<MarketTokenSecurityInfo> syncRisk(String contractAddresses,Long chainIndex,String token){
        TokenSecurityRequest of = TokenSecurityRequest.of(ChainEnum.getChanByIndex(chainIndex).getChainId().toString(), contractAddresses, token,10000);
        List<MarketTokenSecurityInfo> tokenSecurityInfoDOList= new ArrayList<>();
        try {
            var goplusResponse = GoPlusClient.tokenSecurity(of);
            var value = goplusResponse.getValue().getResult();
            if (value == null) return null;
            findMissingAddresses(contractAddresses, value.keySet(), chainIndex, tokenMapper);
            for (Map.Entry<String, ResponseWrapperTokenSecurityResult> entry : value.entrySet()) {
                String key = entry.getKey().toLowerCase();
                ResponseWrapperTokenSecurityResult tokenSecurity = entry.getValue();
                try {

                    LambdaQueryWrapper<MarketTokenSecurityInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(MarketTokenSecurityInfo::getChainIndex, chainIndex);
                    lambdaQueryWrapper.eq(MarketTokenSecurityInfo::getAddress, key);
                    MarketTokenSecurityInfo tokenSecurityDO = tokenSecurityInfoService.getOne(lambdaQueryWrapper);
                    if(tokenSecurityDO == null){
                        tokenSecurityDO = new MarketTokenSecurityInfo();
                    }
                    tokenSecurityDO.setChainIndex(chainIndex);
                    tokenSecurityDO.setAddress(key);
                    tokenSecurityDO.setIsOpenSource("1".equals(tokenSecurity.getIsOpenSource()));
                    tokenSecurityDO.setIsProxy("1".equals(tokenSecurity.getIsProxy()));
                    tokenSecurityDO.setIsMintable("1".equals(tokenSecurity.getIsMintable()));
                    tokenSecurityDO.setOwnerAddress(tokenSecurity.getOwnerAddress());
                    tokenSecurityDO.setCanTakeBackOwnership("1".equals(tokenSecurity.getCanTakeBackOwnership()));
                    tokenSecurityDO.setOwnerChangeBalance("1".equals(tokenSecurity.getOwnerChangeBalance()));
                    tokenSecurityDO.setHiddenOwner("1".equals(tokenSecurity.getHiddenOwner()));
                    tokenSecurityDO.setSelfdestruct("1".equals(tokenSecurity.getSelfdestruct()));
                    tokenSecurityDO.setExternalCall("1".equals(tokenSecurity.getExternalCall()));
                    tokenSecurityDO.setIsInDex("1".equals(tokenSecurity.getIsInDex()));
                    tokenSecurityDO.setCannotBuy("1".equals(tokenSecurity.getCannotBuy()));
                    tokenSecurityDO.setCannotSellAll("1".equals(tokenSecurity.getCannotSellAll()));
                    tokenSecurityDO.setSlippageModifiable("1".equals(tokenSecurity.getSlippageModifiable()));
                    tokenSecurityDO.setIsTrueToken(!"0".equals(tokenSecurity.getIsTrueToken()));
                    tokenSecurityDO.setIsHoneypot("1".equals(tokenSecurity.getIsHoneypot()));
                    tokenSecurityDO.setTransferPausable("1".equals(tokenSecurity.getTransferPausable()));
                    tokenSecurityDO.setIsBlacklisted("1".equals(tokenSecurity.getIsBlacklisted()));
                    tokenSecurityDO.setIsWhitelisted("1".equals(tokenSecurity.getIsWhitelisted()));
                    tokenSecurityDO.setIsAntiWhale("1".equals(tokenSecurity.getIsAntiWhale()));
                    tokenSecurityDO.setAntiWhaleModifiable("1".equals(tokenSecurity.getAntiWhaleModifiable()));
                    tokenSecurityDO.setTradingCooldown("1".equals(tokenSecurity.getTradingCooldown()));
                    tokenSecurityDO.setPersonalSlippageModifiable("1".equals(tokenSecurity.getPersonalSlippageModifiable()));
                    tokenSecurityDO.setCreatorAddress(tokenSecurity.getCreatorAddress());
                    tokenSecurityDO.setIsAirdropScam("1".equals(tokenSecurity.getIsAirdropScam()));
                    tokenSecurityDO.setTrustList("1".equals(tokenSecurity.getTrustList()));
                    tokenSecurityDO.setOtherPotentialRisks(tokenSecurity.getOtherPotentialRisks());
                    tokenSecurityDO.setTotalSupply(tokenSecurity.getTotalSupply());
                    tokenSecurityDO.setNote(tokenSecurity.getNote());
                    tokenSecurityDO.setOwnerBalance(new BigDecimal(tokenSecurity.getOwnerBalance()));
                    tokenSecurityDO.setOwnerPercent(new BigDecimal(tokenSecurity.getOwnerPercent()));
                    tokenSecurityDO.setCreatorPercent(new BigDecimal(tokenSecurity.getCreatorPercent()));
                    tokenSecurityDO.setLpTotalSupply(new BigDecimal(tokenSecurity.getLpTotalSupply()));
                    tokenSecurityDO.setCreatorBalance(new BigDecimal(tokenSecurity.getCreatorBalance()));

                    if (tokenSecurity.getHolders() != null) {
                        tokenSecurityDO.setHolders(convertToJson(tokenSecurity.getHolders()));
                    }
                    if (tokenSecurity.getDex() != null) {
                        tokenSecurityDO.setDex(convertToJson(tokenSecurity.getDex()));
                    }
                    if (tokenSecurity.getFakeToken() != null) {
                        tokenSecurityDO.setFakeToken(convertToJson(tokenSecurity.getFakeToken()));
                    }
                    if (tokenSecurity.getLpHolders() != null) {
                        tokenSecurityDO.setLpHolders(convertToJson(tokenSecurity.getLpHolders()));
                    }
                    if (tokenSecurity.getLpHolderCount() != null) {
                        tokenSecurityDO.setLpHolderCount(Integer.parseInt(tokenSecurity.getLpHolderCount()));
                    }
                    if (tokenSecurity.getHolderCount() != null) {
                        tokenSecurityDO.setHolderCount(Integer.parseInt(tokenSecurity.getHolderCount()));
                    }
                    if (tokenSecurity.getBuyTax() != null && !tokenSecurity.getBuyTax().trim().isEmpty()) {
                        tokenSecurityDO.setBuyTax(new BigDecimal(tokenSecurity.getBuyTax()));
                    }
                    if (tokenSecurity.getSellTax() != null && !tokenSecurity.getSellTax().trim().isEmpty()) {
                        tokenSecurityDO.setSellTax(new BigDecimal(tokenSecurity.getSellTax()));
                    }

                    tokenSecurityUpsert(chainIndex, key, tokenSecurityDO, tokenSecurityInfoService, getRiskLevel(tokenSecurityDO), tokenMapper);
                    tokenSecurityInfoDOList.add(tokenSecurityDO);
                } catch (Exception e) {
                    log.error("Sync Eth Risk {} {} error:", chainIndex, key, e);
                }
            }
        } catch (Exception ignored) {}
        return tokenSecurityInfoDOList;
    }
}
