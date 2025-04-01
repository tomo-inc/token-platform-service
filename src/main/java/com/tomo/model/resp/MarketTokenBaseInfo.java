package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tomo.model.market.SocialInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MarketTokenBaseInfo {
    @JsonIgnore
    private String coinId;
    private Long chainIndex;
    private String address;
    private Boolean isNative;
    private String name;
    private String symbol;
    private String imageUrl;
    private Integer decimals;
    private String totalSupply;
    private Map<String, List<String>> category;
    private Integer riskLevel;
    private SocialInfo social;
}
