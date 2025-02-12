package com.tomo.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenMessageDto {
    private String riseTokenSymbol;
    private String riseTokenAddress;
    private String tokenAddress;
    private String name;
    private String symbol;
    private Integer decimals;
    private String priceChangeH24;
    private String price;
    private String volumeH24;
    private String marketCapUsd;
}