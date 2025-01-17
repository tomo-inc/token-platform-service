package com.tomo.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TradeDTO implements Serializable {
    @JsonIgnore
    private String pair;
    @JsonIgnore
    private String tokenAddress;
    @JsonIgnore
    private String tokenOutAddress;
    @JsonProperty("ty")
    private TradeType type;
    @JsonProperty("a")
    private String amount;
    @JsonProperty("ao")
    private String amountOut;
    @JsonProperty("aiu")
    private String amountInUsd;
    @JsonProperty("aoiu")
    private String amountOutInUsd;
    @JsonProperty("p")
    private Double price;
    @JsonProperty("piu")
    private Double priceInUsd;
    @JsonProperty("ts")
    private String tokenSymbol;
    @JsonProperty("tos")
    private String tokenOutSymbol;
    @JsonProperty("s")
    private String signature;
    @JsonProperty("m")
    private String maker;
    @JsonProperty("t")
    private Long time;

    public enum TradeType {
        buy,
        sell
    }
}