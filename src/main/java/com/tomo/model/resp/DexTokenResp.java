package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DexTokenResp {
    private List<DexData> data;
    private List<DexData.Pool> included;

    @Data
    public static class DexData {
        private String id;
        private String type;
        private Attributes attributes;
        private DataInnerRelationship relationships;

        @Data
        public static class Attributes {
            private String address;
            private String name;
            private String symbol;
            @JsonProperty("image_url")
            private String imageUrl;
            @JsonProperty("coingecko_coin_id")
            private String coingeckoCoinId;
            private Integer decimals;
            @JsonProperty("total_supply")
            private BigDecimal totalSupply;
            @JsonProperty("price_usd")
            private BigDecimal priceUsd;
            @JsonProperty("fdv_usd")
            private BigDecimal fdvUsd;
            @JsonProperty("total_reserve_in_usd")
            private String totalReserveInUsd;
            @JsonProperty("reserve_in_usd")
            private String reserveInUsd;
            @JsonProperty("volume_usd")
            private TimeInterval volumeUsd;
            @JsonProperty("market_cap_usd")
            private BigDecimal marketCapUsd;
            @JsonProperty("price_change_percentage")
            private TimeInterval priceChangePercentage;
        }

        @Data
        public static class TimeInterval {
            private BigDecimal h24;
            private BigDecimal h6;
            private BigDecimal h1;
            private BigDecimal m5;
        }

        @Data
        public static class Pool {
            private String id;
            private String type;
            private Attributes attributes;
            private Relationships relationships;
        }

        @Data
        public static class Relationships {
            @JsonProperty("base_token")
            private BaseToken baseToken;
            @JsonProperty("quote_token")
            private BaseToken quoteToken;
            private BaseToken network;
            private BaseToken dex;
        }

        @Data
        public static class BaseToken {
            private TokenData data;
        }

        @Data
        public static class TokenData {
            private String id;
            private String type;
        }

        @Data
        public static class DataInnerRelationship {
            @JsonProperty("top_pools")
            private DataInnerList topPools;
        }

        @Data
        public static class DataInnerList {
            private List<TokenData> data;
        }
    }

}

