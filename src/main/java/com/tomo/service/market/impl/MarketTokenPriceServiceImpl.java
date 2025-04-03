package com.tomo.service.market.impl;

import com.tomo.kafka.message.PriceKafkaMessage;
import com.tomo.mapper.MarketTokenPriceMapper;
import com.tomo.model.ChainUtil;
import com.tomo.model.market.MarketTokenPrice;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenDetailInfo;
import com.tomo.service.market.MarketTokenDaoService;
import com.tomo.service.market.MarketTokenPriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MarketTokenPriceServiceImpl implements MarketTokenPriceService {

    @Autowired
    private MarketTokenPriceMapper marketTokenPriceMapper;
    @Autowired
    private MarketTokenDaoService marketTokenDaoService;

    @Override
    public void handlePriceMsg(List<PriceKafkaMessage> msgList) {
        Date now = new Date();
        for (PriceKafkaMessage message : msgList) {
            MarketTokenPrice marketTokenPrice = marketTokenPriceMapper.selectMarketTokenPrice(Long.valueOf(message.getChainIndex()), message.getAddress());
            if (marketTokenPrice == null) {
                return;
            }
            marketTokenPrice.setRealPrice(new BigDecimal(message.getPriceUsd()));
            marketTokenPrice.setVolume24h(new BigDecimal(message.getVolume24h()));
            marketTokenPrice.setChange24h(new BigDecimal(message.getChange24h()));
            marketTokenPriceMapper.updateMarketTokenPrice(marketTokenPrice);

            MarketTokenReq req = MarketTokenReq.builder().chainIndex(Long.valueOf(message.getChainIndex())).address(message.getAddress()).build();
            List<MarketTokenReq> reqList = Collections.singletonList(req);

            Map<String, MarketTokenDetailInfo> cache = marketTokenDaoService.queryFromCache(reqList);
            if (MapUtils.isEmpty(cache)) {
                cache = marketTokenDaoService.queryFromDb(reqList);
            }

            MarketTokenDetailInfo marketTokenDetailInfo = cache.get(ChainUtil.getTokenKey(Long.valueOf(message.getChainIndex()), message.getAddress()));
            if (marketTokenDetailInfo != null) {
                marketTokenDaoService.saveCache(Collections.singletonList(marketTokenDetailInfo));
            }
        }
    }
}
