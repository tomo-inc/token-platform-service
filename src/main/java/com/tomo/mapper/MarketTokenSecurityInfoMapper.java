package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.market.MarketTokenSecurityInfo;
import com.tomo.model.req.MarketTokenReq;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketTokenSecurityInfoMapper extends BaseMapper<MarketTokenSecurityInfo> {

    MarketTokenSecurityInfo selectMarketTokenSecurityInfoById(long id);

    void insertMarketTokenSecurityInfo(MarketTokenSecurityInfo marketTokenSecurityInfo);

    void updateMarketTokenSecurityInfo(MarketTokenSecurityInfo marketTokenSecurityInfo);

    void deleteMarketTokenSecurityInfo(long id);

    List<MarketTokenSecurityInfo> list(@Param("list") List<MarketTokenReq> list);
}