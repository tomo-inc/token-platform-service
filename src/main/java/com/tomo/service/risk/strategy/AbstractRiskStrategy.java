package com.tomo.service.risk.strategy;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.RiskLevel;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.service.market.MarketTokenSecurityService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public abstract class AbstractRiskStrategy implements RiskStrategy {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertToJson(Object input) {
        try {
            if (input instanceof String) {
                return (String) input;
            } else {
                return objectMapper.writeValueAsString(input);
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
            return null;
        }
    }

    public void findMissingAddresses(String contractAddresses, Set<String> valueKeys, Long chainIndex, MarketTokenInfoMapper tokenMapper) {
        List<String> addressList = Arrays.asList(contractAddresses.split(","));

        List<String> missingAddresses = addressList.stream()
                .filter(address -> !valueKeys.contains(address))
                .toList();

        for (String address : missingAddresses) {
            LambdaUpdateWrapper<MarketTokenInfo> lambdaQueryWrapper = new LambdaUpdateWrapper<>();
            lambdaQueryWrapper.eq(MarketTokenInfo::getChainIndex, chainIndex);
            lambdaQueryWrapper.eq(MarketTokenInfo::getAddress, address);
            lambdaQueryWrapper.set(MarketTokenInfo::getRiskLevel, RiskLevel.NONE.getCode());
            tokenMapper.update(null, lambdaQueryWrapper);
        }
    }

    public void tokenSecurityUpsert(Long chainIndex, String address, MarketTokenSecurityInfo tokenSecurityDO, MarketTokenSecurityService tokenSecurityInfoService, RiskLevel riskLevel, MarketTokenInfoMapper tokenMapper) {
        tokenSecurityInfoService.saveOrUpdate(tokenSecurityDO);
        LambdaUpdateWrapper<MarketTokenInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MarketTokenInfo::getChainIndex, chainIndex)
                .eq(MarketTokenInfo::getAddress, address)
                .set(MarketTokenInfo::getRiskLevel, riskLevel.getCode());
        tokenMapper.update(null, updateWrapper);
    }
}
