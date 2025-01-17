package com.tomo.model.resp;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OnchainOHLCVResp {
    private OhlcvData data;
    private Meta meta;

    @Data
    public static class OhlcvData {
        private String id;
        private String type;
        private Attributes attributes;
    }

    @Data
    public static class Attributes {
        @JsonProperty("ohlcv_list")
        private List<List<Double>> ohlcvList;
    }

    @Data
    public static class Meta {
        private Base base;
        private Base quote;
    }

    @Data
    public static class Base {
        private String address;
        private String name;
        private String symbol;
        @JsonProperty("coingecko_coin_id")
        private String coingeckoCoinId;
    }

}
