package com.tomo.model.market;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MarketTokenInfo {
    private Long id;
    private Long chainIndex;
    private String address;
    private Boolean isNative;
    private String name;
    private String symbol;
    private String imageUrl;
    private Integer decimals;
    private SocialInfo social;
    private Integer riskLevel;
    private Boolean forceSafe;
    private Timestamp updateTime;
    private Timestamp createTime;

}