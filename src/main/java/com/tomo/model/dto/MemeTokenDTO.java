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
public class MemeTokenDTO {
    private Long id;
    private String tokenName;
    private String tokenAddress;
    private String tokenSymbol;
    private Integer tokenPrecision;
    private String imageUrl;
    private String raiseTokenSymbol;
    private String raiseTokenAddress;
    private String priceUsd;
    private String priceChangeH24;
    private String volumeH24;
    private String marketCapUsd;
    private Date createTime;
    private Date updateTime;
    private Long publishTime;
    private Double progress;
    private Boolean launchOnPancake;
}