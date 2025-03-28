package com.tomo.model.resp;

import lombok.Data;

public class MarketTokenHistory {
    private String addr;
    private String router;
    private String factory;
    private String pool;
    private String side;
    private String amount;
    private Double price;
    private Double value;
    private Token token;
    private Long time;
    private String txFrom;
    private String txHash;

    @Data
    public static class Token {
        private String address;
        private String name;
        private String symbol;
        private Integer decimals;
    }

}
