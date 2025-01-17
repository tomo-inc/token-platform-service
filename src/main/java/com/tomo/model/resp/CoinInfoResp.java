package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
public class CoinInfoResp {
    private String id;
    private String symbol;
    private String name;
    @JsonProperty("web_slug")
    private String webSlug;
    @JsonProperty("asset_platform_id")
    private String assetPlatformId;
    @JsonProperty("platforms")
    private Map<String, String> platforms;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("detail_platforms")
    private Map<String, DetailPlatform> detailPlatforms;
    private Image image;
    @JsonProperty("contract_address")
    private String contractAddress;
    @JsonProperty("market_data")
    private MarketData marketData;
    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;

    @Data
    public static class DetailPlatform {
        @JsonProperty("decimal_place")
        private Integer decimalPlace;
        @JsonProperty("contract_address")
        private String contractAddress;
    }

    @Data
    public static class MarketData {
        @JsonProperty("current_price")
        private ValueData currentPrice;
        @JsonProperty("market_cap")
        private ValueData marketCap;
        @JsonProperty("fully_diluted_valuation")
        private ValueData fullyDilutedValuation;
        @JsonProperty("price_change_24h")
        private Double priceChange24H;
        @JsonProperty("price_change_percentage_24h")
        private Double priceChangePercentage24H;
        @JsonProperty("market_cap_change_24h")
        private BigDecimal marketCapChange24H;
        @JsonProperty("market_cap_change_percentage_24h")
        private BigDecimal marketCapChangePercentage24H;
        @JsonProperty("total_supply")
        private BigDecimal totalSupply;
        @JsonProperty("max_supply")
        private BigDecimal maxSupply;
        @JsonProperty("circulating_supply")
        private BigDecimal circulatingSupply;
        @JsonProperty("last_updated")
        private OffsetDateTime lastUpdated;
    }

    @Data
    public static class ValueData {
        private BigDecimal usd;
    }

    @Data
    public static class Image {
        private String thumb;
        private String small;
        private String large;
    }

    @Data
    public static class Links {
        @JsonProperty("homepage")
        private List<String> homepage;
        @JsonProperty("twitter_screen_name")
        private String twitterScreenName;
        @JsonProperty("facebook_username")
        private String facebookUsername;
        @JsonProperty("telegram_channel_identifier")
        private String telegramChannelIdentifier;
    }
}