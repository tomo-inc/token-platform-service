package com.tomo.service.risk.strategy;

import com.tomo.model.ChainPlatformType;
import com.tomo.model.market.MarketTokenSecurityInfo;

import java.util.List;
import java.util.Map;

public interface RiskStrategy {
    ChainPlatformType getType();

    List<MarketTokenSecurityInfo> syncRisk(String contractAddresses, Long chainIndex, String token);
}
