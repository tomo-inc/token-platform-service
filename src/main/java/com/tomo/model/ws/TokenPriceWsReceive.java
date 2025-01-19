package com.tomo.model.ws;

import lombok.Data;

import java.util.Objects;


@Data
public class TokenPriceWsReceive {
    String status;
    long chainId;
    String address;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenPriceWsReceive receive = (TokenPriceWsReceive) o;
        return chainId == receive.chainId && Objects.equals(address, receive.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chainId, address);
    }
}
