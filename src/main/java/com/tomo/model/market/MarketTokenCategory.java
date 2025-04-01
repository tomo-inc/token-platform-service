package com.tomo.model.market;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MarketTokenCategory {
    private Long id;
    private String coinId;
    private Long chainIndex;
    private String address;
    private String category;
    private List<String> tags;
    private Timestamp updateTime;
    private Timestamp createTime;
}