package com.tomo.service.risk;


import com.tomo.model.dto.TokenRiskGoplusDTO;
import com.tomo.model.req.RiskTokenDataReq;

public interface RiskTokenDataService<T> {
    // 三方api
    T fetchRiskToken(RiskTokenDataReq query);

    boolean saveRiskToken(T riskToken);

    boolean updateRiskToken(TokenRiskGoplusDTO riskToken);

    T getRiskToken(RiskTokenDataReq riskTokenList);

}
