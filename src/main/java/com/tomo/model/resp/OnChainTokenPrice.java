package com.tomo.model.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OnChainTokenPrice {
    private long chainId;

    private String tokenContractAddress;

    private BigDecimal priceUsd;
}
