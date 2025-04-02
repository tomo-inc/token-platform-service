package com.tomo.service.market;

import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.model.resp.MarketTokenDetailInfo;

import java.util.List;
import java.util.Map;

public interface MarketTokenDaoService {
    Map<String, MarketTokenDetailInfo> queryFromCache(List<MarketTokenReq> list);

    void saveCache(List<MarketTokenDetailInfo> list);

    Map<String, MarketTokenDetailInfo> queryFromDb(List<MarketTokenReq> list);


    List<MarketTokenBaseInfo> queryBaseInfo(List<MarketTokenReq> list);
}
