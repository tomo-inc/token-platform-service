package com.tomo.model.resp;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CoinMarketDataResp {
    private String id;
    private String symbol;
    private String name;
    private String image;
    @JsonProperty("current_price")
    private String currentPrice;
    @JsonProperty("market_cap")
    private String marketCap;
    @JsonProperty("market_cap_rank")
    private String marketCapRank;
    @JsonProperty("fully_diluted_valuation")
    private String fullyDilutedValuation;
    @JsonProperty("total_volume")
    private String totalVolume;
    @JsonProperty("high_24h")
    private String high24h;
    @JsonProperty("low_24h")
    private String low24h;
    @JsonProperty("last_updated")
    private OffsetDateTime lastUpdated;
}