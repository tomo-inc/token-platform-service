package com.tomo.model.market.enums;

import com.tomo.model.ChainEnum;
import lombok.Getter;

@Getter
public enum GDexChainEnum {

    Ethereum(ChainEnum.ETH, "ethereum"),
    Base(ChainEnum.BASE, "base"),
    Linea(ChainEnum.LINEA, "linea"),
    Arbitrum_One(ChainEnum.ARBITRUM, "arbitrum"),
    Avalanche_Chain(ChainEnum.AVAX, "avalanchec"),
    BNB_Chain(ChainEnum.BSC, "bnbchain"),
    //    Fantom(ChainEnum.fantom, "fantom"),  // 还没支持
    Optimism(ChainEnum.OPTIMISM, "optimism"),
    Polygon(ChainEnum.POLYGON, "polygon"),
    zksync(ChainEnum.ZKSYNC, "zksync"),
    Tron(ChainEnum.TRON, "tron"),
    //    Core(ChainEnum.core,"core"), // 还没支持
    //zklink(ChainEnum.ZKLINK,"zklink"), // 还没支持
    opBNB(ChainEnum.OP_BNB, "opbnb"),


    ;


    private final ChainEnum chainEnum;
    private final String name;

    GDexChainEnum(ChainEnum chainEnum, String ethereum) {
        this.chainEnum = chainEnum;
        this.name = ethereum;
    }

    public static GDexChainEnum getByChainIndex(Long chainIndex) {
        for (GDexChainEnum e : GDexChainEnum.values()) {
            if (e.getChainEnum().getChainIndex().equals(chainIndex)) {
                return e;
            }
        }
        return null;
    }

//    private final Long chainIndex;
//    private final Long chainId;
//    private final String chainName;
//    private final Boolean isEVM;
}
