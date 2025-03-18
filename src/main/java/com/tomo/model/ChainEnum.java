package com.tomo.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum ChainEnum {
    ETH(100L, 1L, "Ethereum"),
    BSC(5600L, 56L, "BNB Chain"),
    ARBITRUM(4216100L, 42161L, "Arbitrum One"),
    OPTIMISM(1000L, 10L, "Optimism"),
    LINEA(5914400L, 59144L, "Linea"),
    ZKSYNC(32400L, 324L, "zkSync Era"),
    POLYGON(13700L, 137L, "Polygon"),
    BASE(845300L, 8453L, "Base"),
    BLAST(8145700L, 81457L, "Blast"),
    AVAX(4311400L, 43114L, "Avalanche C"),
    BOB(6080800L, 60808L, "BOB"),
    SCROLL(53435200L, 534352L, "Scroll"),
    OP_BNB(20400L, 204L, "opBNB"),
    B3(833300L, 8333L, "B3 Mainnet"),
    WORLD_CHAIN(48000L, 480L, "World Chain"),
    DUCK_CHAIN(554500L, 5545L, "DuckChain Mainnet"),
    AI_LAYER(264900L, 2649L, "AILayer"),
    BIT_LAYER(20090100L, 200901L, "Bitlayer"),
    MERLINE_CHAIN(420000L, 4200L, "Merlin Chain"),
    CORN(2100000000L, 21000000L, "Corn"),
    B2_Network(22300L, 223L, "B² Network"),
    GRAVITY_ALPHA(162500L, 1625L, "Gravity Alpha"),
    NEO_X(4776300L, 47763L, "Neo X Mainnet"),
    SEI_EVM(132900L, 1329L, "Sei EVM"),
    LORENZO(832900L, 8329L, "Lorenzo"),
    PROM(22700L, 227L, "Prom"),
    DUCK_CHAIN_TEST(20210500L, 202105L, "DuckChain Testnet"),
    STORY_ODYSSEY_TEST(151600L, 1516L, "Story Odyssey Testnet"),
    BERACHAIN_BARTIO(8009400L, 80094L, "Berachain Mainnet"),
    BERACHAIN_BARTIO_TEST(8008400L, 80084L, "Berachain bArtio Testnet"),
    BOTANIX_TEST(363600L, 3636L, "Botanix Testnet"),
    TAC_TEST(239000L, 2390L, "TAC Testnet"),
    BTC(0L, 0L, "Bitcoin"),
    SOL(50100L, 501L, "Solana"),
    TON(110000L, 1100L, "TON"),
    TRON(1948400L, 19484L, "Tron"),
    SUI(78400L, 784L, "SUI"),
    TON_TEST(110100L, 1101L, "TON Testnet"),
    DOGE(300L, 3L, "Dogecoin"),
    COSMOS(400L, 4L, "Cosmos Hub"),
    ;

    private final Long chainIndex;
    private final Long chainId;
    private final String chainName;

    ChainEnum(final Long chainIndex, final Long chainId, final String chainName) {
        this.chainIndex = chainIndex;
        this.chainId = chainId;
        this.chainName = chainName;
    }

    public static ChainEnum getChainEnum(final String name) {
        ChainEnum[] values = ChainEnum.values();
        for (ChainEnum value : values) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return null;
    }

    public static ChainEnum getChanByIndex(final Long chainIndex) {
        ChainEnum[] values = ChainEnum.values();
        for (ChainEnum value : values) {
            if (Objects.equals(value.getChainIndex(), chainIndex)) {
                return value;
            }
        }
        throw new RuntimeException("chainIndex:" + chainIndex + " not match any value");
    }
}
