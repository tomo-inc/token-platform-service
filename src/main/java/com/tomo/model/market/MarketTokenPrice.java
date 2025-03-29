package com.tomo.model.market;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class MarketTokenPrice {
    private Long id;
    private Long coinId;
    private Long chainIndex;
    private String address;
    private String poolAddress;
    private Boolean isPoolBaseToken;
    private BigDecimal liquidityUsd;
    private BigDecimal realPrice;
    private BigDecimal volume24h;
    private BigDecimal change24h;
    private BigDecimal marketCap;
    private BigDecimal fdvUsd;
    private Timestamp updateTime;
    private Timestamp createTime;
}