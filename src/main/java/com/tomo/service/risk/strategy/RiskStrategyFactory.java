package com.tomo.service.risk.strategy;

import com.tomo.model.ChainPlatformType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RiskStrategyFactory {
    final private List<RiskStrategy> strategyList;

    private static final Map<ChainPlatformType, RiskStrategy> strategyMap = new HashMap<>();

    @PostConstruct
    public void init() {
        if (!CollectionUtils.isEmpty(strategyList)) {
            strategyList.forEach(strategy -> strategyMap.put(strategy.getType(), strategy));
        }
    }

    public RiskStrategy getStrategy(ChainPlatformType type) {
        RiskStrategy riskStrategy = strategyMap.get(type);
        if (riskStrategy == null) {
            throw new UnsupportedOperationException("Unsupported chain platform type: " + type);
        }
        return riskStrategy;
    }
}
