package com.tomo.model;

public enum ChainInfoEnum {
    UNKNOWN("unknown","unknown",-1,-1),
    ETH("ethereum", "eth",1, 1),
    BASE("base", "base",8453, 8453),;
    final String coingeckoChainName;
    final String coingeckoOnchainName;
    final long chainId;
    final long okxId;
    ChainInfoEnum(String coingeckoChainName, String coingeckoOnchainName, long chainId, long okxId) {
        this.coingeckoChainName = coingeckoChainName;
        this.coingeckoOnchainName = coingeckoOnchainName;
        this.chainId = chainId;
        this.okxId = okxId;
    }

    public String getCoingeckoChainName() {
        return coingeckoChainName;
    }

    public String getCoingeckoOnchainName() {
        return coingeckoOnchainName;
    }

    public long getChainId() {
        return chainId;
    }

    public long getOkxId() {
        return okxId;
    }
}
