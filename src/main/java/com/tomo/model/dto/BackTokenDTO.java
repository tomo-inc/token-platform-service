package com.tomo.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BackTokenDTO {

    private String name;

    private String displayName;

    private String symbol;

    private String imageUrl;

    private Integer decimals;

    private String chain;

    private Boolean isNative;

    private Boolean isTomoji;

    private Integer tomojiId;

    private String address;

    private BigDecimal priceUsd;

    private Double priceChangeH24;

    private BigDecimal volumeH24;

    private BigDecimal marketCapUsd;

    private BigDecimal fdvUsd;

    private BigDecimal liquidityUsd;

    private BigDecimal totalSupply;

    private String websiteUrl;

    private String twitterUrl;

    private String telegramUrl;

    private String poolAddress;

    private Boolean isPoolBaseToken;

    private Boolean supportRango;

    private String coingeckoCoinId;

    private Boolean forceSafe;
}
