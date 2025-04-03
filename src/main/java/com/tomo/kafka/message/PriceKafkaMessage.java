package com.tomo.kafka.message;

import lombok.Data;

@Data
public class PriceKafkaMessage {

    private String chainIndex;
    // 代币合约地址，主币传""
    private String address;
    // 最后更新时间：时间戳，精确到秒
    private String time;
    // 币价
    private String priceUsd;
    // 24小时交易量
    private String volume24h;
    // 24h价格波动
    private String change24h;
}
