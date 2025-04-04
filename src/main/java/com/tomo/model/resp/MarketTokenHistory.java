package com.tomo.model.resp;

import lombok.Data;

@Data
public class MarketTokenHistory {
    private String txHash;
    private String txFrom;
    private String txTo;
    private String side;
    private String amount;
    private String price;
    private String value;
    private Token token;
    private Long time;


    @Data
    public static class Token {
        private String address;
        private String name;
        private String symbol;
        private Integer decimals;
    }

}
