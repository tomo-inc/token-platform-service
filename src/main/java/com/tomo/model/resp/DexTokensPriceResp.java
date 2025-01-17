package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class DexTokensPriceResp {

    private DexTokenPricesData data;

    @Data
    public static class DexTokenPricesData {
        private String id;
        private String type;
        private Attributes attributes;
    }

    @Data
    public static class Attributes {
        @JsonProperty("token_prices")
        private Map<String, BigDecimal> tokenPrices;
    }
}