package com.tomo.model.market;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceKafkaMessage {
    private Long chainIndex;

    // 代币合约地址，主币传""
    private String address;
    // 最后更新时间：时间戳，精确到秒
    private Long time;
    // 币价
    private BigDecimal priceUsd;
    // 24小时交易量
    private BigDecimal volume24h;
    // 24h价格波动
    private BigDecimal change24h;

}
