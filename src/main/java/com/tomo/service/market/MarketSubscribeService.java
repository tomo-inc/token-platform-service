package com.tomo.service.market;

import com.tomo.model.req.MarketSubscribeReq;
import com.tomo.model.resp.MarketSubscribeResp;

import java.util.List;

public interface MarketSubscribeService {
    void batchSubscribe(List<MarketSubscribeReq> req);

    void unSubscribe(List<MarketSubscribeReq> req);

    List<MarketSubscribeResp> list(Long chainIndex, String address, Integer pageNum, Integer pageSize);
}
