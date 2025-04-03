package com.tomo.mapper;

import com.tomo.model.market.MarketTokenPrice;
import com.tomo.model.req.MarketTokenReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketTokenPriceMapper {

    MarketTokenPrice selectMarketTokenPrice(Long chainIndex, String address);

    MarketTokenPrice selectMarketTokenPriceById(long id);

    void insertMarketTokenPrice(MarketTokenPrice marketTokenPrice);

    void updateMarketTokenPrice(MarketTokenPrice marketTokenPrice);

    void deleteMarketTokenPrice(long id);

    List<MarketTokenPrice> queryByCoinIds(List<String> coinIdList);

    void batchInsert(List<MarketTokenPrice> tokenPriceList);

    MarketTokenPrice getByCoinId(String coinId);
}