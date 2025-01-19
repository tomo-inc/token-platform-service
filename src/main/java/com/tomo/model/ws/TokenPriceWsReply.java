package com.tomo.model.ws;

import lombok.Data;

@Data
public class TokenPriceWsReply {
    long chainId;
    String address;
    String price;
}
