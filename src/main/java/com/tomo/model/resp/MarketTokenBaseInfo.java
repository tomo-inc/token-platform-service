package com.tomo.model.resp;

import com.tomo.model.market.SocialInfo;
import lombok.Data;

import java.util.List;

@Data
public class MarketTokenBaseInfo {
    private int chainIndex;
    private String address;
    private boolean isNative;
    private String name;
    private String symbol;
    private String imageUrl;
    private int decimals;
    private Long totalSupply;
    private List<String> category;
    private List<String> tags;
    private int riskLevel;
    private SocialInfo social;
}
