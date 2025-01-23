package com.tomo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
        return ChainUtil.getCommonKey(chainId,address);
    }
}
