package com.tomo.model.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoRes {
    private String raisedAmount;
    private String saleAmount;
    private String totalAmount;
    private String address;
    private String image;
    private TokenPriceDto tokenPrice;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenPriceDto{
        private String price;
        private String maxPrice;
    }
}
