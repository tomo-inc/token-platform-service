package com.tomo.service.market.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.ChainEnum;
import com.tomo.model.ChainPlatformType;
import com.tomo.model.market.enums.RiskLevel;
import com.tomo.model.dto.ChainDTO;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.service.market.MarketTokenSecurityJobService;
import com.tomo.service.risk.strategy.RiskStrategy;
import com.tomo.service.risk.strategy.RiskStrategyFactory;
import io.gopluslabs.client.GoPlusClient;
import io.gopluslabs.client.auth.SignatureOauth;
import io.gopluslabs.client.request.AccessTokenRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketTokenSecurityJobServiceImpl implements MarketTokenSecurityJobService {

    @Value("${goplus.key}")
    private String goplusKey;
    @Value("${goplus.secret}")
    private String goplusSecret;

    @Autowired
    private ChainServiceImpl chainService;
    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;
    @Autowired
    private RiskStrategyFactory riskStrategyFactory;

    public static final String GOPLUS_TOKEN_BY_ONE_HOUR = "CACHE_GOPLUS_TOKEN_BY_ONE_HOUR#3600";

    @Override
    public void syncAllTokenSecurityInfo() {
        if (!StringUtils.hasLength(goplusKey)) return;
        List<ChainDTO> evmChains = getEvmChains();
        for (ChainDTO chain : evmChains) {
            if (chain.getChainId() == null) continue;
            ChainEnum chainEnum = ChainEnum.getByChainId(chain.getChainId().longValue());
            if (chainEnum == null) continue;

            List<MarketTokenInfo> ethTokenList = marketTokenInfoMapper.getSecurityList(chainEnum.getChainIndex());
            updateTokenSafe(chainEnum.getChainIndex());
            syncSecurityToDb(ethTokenList, ChainPlatformType.EVM, chainEnum.getChainIndex());
        }
        List<MarketTokenInfo> solanaTokenList = marketTokenInfoMapper.getSecurityList(ChainEnum.SOL.getChainIndex());
        updateTokenSafe(ChainEnum.SOL.getChainIndex());
        syncSecurityToDb(solanaTokenList, ChainPlatformType.SOLANA, ChainEnum.SOL.getChainIndex());
    }

    @Override
    public void syncNullTokenSecurityInfo() {
        if (!StringUtils.hasLength(goplusKey)) return;
        List<ChainDTO> evmChains = getEvmChains();
        for (ChainDTO chain : evmChains) {
            if (chain.getChainId() == null) continue;
            ChainEnum chainEnum = ChainEnum.getByChainId(chain.getChainId().longValue());
            if (chainEnum == null) continue;

            List<MarketTokenInfo> ethTokenList = marketTokenInfoMapper.getNullSecurityList(chainEnum.getChainIndex());
            syncSecurityToDb(ethTokenList, ChainPlatformType.EVM, chainEnum.getChainIndex());
        }
        List<MarketTokenInfo> solanaTokenList = marketTokenInfoMapper.getNullSecurityList(ChainEnum.SOL.getChainIndex());
        syncSecurityToDb(solanaTokenList, ChainPlatformType.SOLANA, ChainEnum.SOL.getChainIndex());
    }

    public List<ChainDTO> getEvmChains() {
        List<ChainDTO> chains = chainService.listAll();
        return chains.stream()
                .filter(chain -> ChainPlatformType.EVM.equals(chain.getPlatformType()) && chain.getChainId() != null && chain.getChainId() > 0)
                .toList();
    }


    public void updateTokenSafe(Long chainIndex) {
        LambdaUpdateWrapper<MarketTokenInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MarketTokenInfo::getForceSafe, 1)
                .eq(MarketTokenInfo::getChainIndex, chainIndex)
                .set(MarketTokenInfo::getRiskLevel, RiskLevel.SAFE.getCode());
        marketTokenInfoMapper.update(null, updateWrapper);

        LambdaUpdateWrapper<MarketTokenInfo> updateNativeWrapper = new LambdaUpdateWrapper<>();
        updateNativeWrapper.eq(MarketTokenInfo::getIsNative, 1)
                .eq(MarketTokenInfo::getChainIndex, chainIndex)
                .set(MarketTokenInfo::getRiskLevel, RiskLevel.SAFE.getCode());
        marketTokenInfoMapper.update(null, updateNativeWrapper);
    }

    public void syncSecurityToDb(List<MarketTokenInfo> tokenList, ChainPlatformType chain, Long chainIndex) {
        List<List<MarketTokenInfo>> chunks = splitList(tokenList, 100);
        RiskStrategy strategy = riskStrategyFactory.getStrategy(chain);
        var goplusToken = getGoplusToken();
        if (goplusToken == null) return;
        for (List<MarketTokenInfo> chunk : chunks) {
            try {
                String contractAddresses = chunk.stream()
                        .map(MarketTokenInfo::getAddress)
                        .collect(Collectors.joining(","));
                strategy.syncRisk(contractAddresses, chainIndex, goplusToken);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread was interrupted", e);
            }
        }
    }

    public String getGoplusToken(){
        Long time = System.currentTimeMillis() / 1000;
        String sign = SignatureOauth.signatureSha1(goplusKey, time, goplusSecret);
        AccessTokenRequest of = AccessTokenRequest.of(goplusKey, sign, time, 1000);
        try {
            var result = GoPlusClient.getAccessToken(of);
            return result.getValue().getResult().getAccessToken();
        }catch (Exception e){
            log.error("get goplus token error: ", e);
            return null;
        }
    }

    public <T> List<List<T>> splitList(List<T> list, int size) {
        return new AbstractList<List<T>>() {
            @Override
            public List<T> get(int index) {
                int start = index * size;
                int end = Math.min(start + size, list.size());
                return list.subList(start, end);
            }

            @Override
            public int size() {
                return (int) Math.ceil((double) list.size() / size);
            }
        };
    }
}
