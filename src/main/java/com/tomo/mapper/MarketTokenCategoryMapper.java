package com.tomo.mapper;

import com.tomo.model.market.MarketTokenCategory;
import com.tomo.model.req.MarketTokenCategoryReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketTokenCategoryMapper {

    MarketTokenCategory selectMarketTokenCategoryById(long id);

    void insertMarketTokenCategory(MarketTokenCategory marketTokenCategory);

    void updateMarketTokenCategory(MarketTokenCategory marketTokenCategory);

    void deleteMarketTokenCategory(long id);

    List<MarketTokenCategory> queryByCoinIds(List<Long> coinIds);

    List<MarketTokenCategory> pageQuery(MarketTokenCategoryReq req);
}