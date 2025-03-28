package com.tomo.model.resp;

import com.tomo.model.market.SocialInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketTokenDetailInfo extends MarketTokenBaseInfo{
    private String liquidityUsd;
    private String realPrice;
    private String volume24h;
    private String change24h;
    private String marketCap;
    private String fdvUsd;
}
