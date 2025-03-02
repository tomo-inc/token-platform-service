package com.tomo.model.resp;

import lombok.Data;

@Data
public class OnChainTokenInfo {
    private long chainId;

    private String tokenContractAddress;

    private String chainName;

    private String tokenName;

    private String tokenSymbol;

    private Integer decimals;
}
