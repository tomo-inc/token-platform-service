package com.tomo.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MarketSubscribeInfo {
    private Long id;
    private Long chainIndex;
    private String address;
    private Integer type;
    private Integer status;
    private Timestamp createdTime;
    private Timestamp updatedTime;

}