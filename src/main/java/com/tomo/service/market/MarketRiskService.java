package com.tomo.service.market;

import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketRiskDetail;

import java.util.List;

public interface MarketRiskService {
    List<MarketRiskDetail> list(List<MarketTokenReq> list);
}
