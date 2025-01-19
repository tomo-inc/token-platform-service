package com.tomo.model;

import lombok.Data;

import java.util.Objects;

@Data
public class TokenBase {
    String address;
    Long chainId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenBase tokenBase = (TokenBase) o;
        return Objects.equals(chainId, tokenBase.chainId) && Objects.equals(address, tokenBase.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, chainId);
    }

    public static boolean isNull(TokenBase tokenBase) {
        return tokenBase.getAddress() == null && tokenBase.getChainId() == 0;
    }

    public String getId() {
        return address + chainId;
    }
}
