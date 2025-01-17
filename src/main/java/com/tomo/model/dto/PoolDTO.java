package com.tomo.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class PoolDTO implements Serializable {
    private String address;
    private String protocolFamily;
    private String baseTokenAddress;
    private String quoteTokenAddress;
    private TokenCategoryCoinGeckoDTO baseToken;
    private TokenCategoryCoinGeckoDTO quoteToken;
    private BigDecimal baseTokenAmount;
    private BigDecimal baseTokenAmountInUsd;
    private BigDecimal quoteTokenAmount;
    private BigDecimal quoteTokenAmountInUsd;
}
