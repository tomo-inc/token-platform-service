package com.tomo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private BigDecimal raiseValue;
    private Long publishTime;
    private Double progress;
    private Boolean launchOnPancake;
    private Boolean fourMemeToken;
    private String riseTokenSymbol;
    private String riseTokenAddress;

    private Long id;

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

    private String risk;

    private Boolean forceSafe;

    private Date createdTime;

    private Date updatedTime;
}