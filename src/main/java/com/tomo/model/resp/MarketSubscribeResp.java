package com.tomo.model.resp;

import lombok.Data;

@Data
public class MarketSubscribeResp {

    private Long chainIndex;
    private String address;
    private String symbol;
    private Integer status;
    private Integer type;
}
