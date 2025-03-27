package com.tomo.mapper;

import com.tomo.model.market.MarketTokenInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketTokenInfoMapper {

    MarketTokenInfo selectMarketTokenInfoById(long id);

    void insertMarketTokenInfo(MarketTokenInfo marketTokenInfo);

    void updateMarketTokenInfo(MarketTokenInfo marketTokenInfo);

    void deleteMarketTokenInfo(long id);
}