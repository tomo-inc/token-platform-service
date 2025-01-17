package com.tomo.model.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenPriceResp {
    @JsonProperty("usd")
    Double realPrice;
    @JsonProperty("usd_24h_vol")
    Double volume24h;
    @JsonProperty("usd_24h_change")
    Double change24h;
    @JsonProperty("usd_market_cap")
    Double marketCap;
}