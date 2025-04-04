package com.tomo.model.market;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GDexTxInfo {
    private String txHash;
    private String txFrom;
    private String txTo;
    private String pool;
    private Long time;
    private List<Token> tokens;
    private String pair;

    @Data
    public static class Token {
        private String address;
        private String name;
        private String symbol;
        private Integer decimals;
        private String side;
        private BigDecimal price;
        private BigDecimal amount;
        private BigDecimal value;
    }
}