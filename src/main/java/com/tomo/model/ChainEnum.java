package com.tomo.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum ChainEnum {
    ETH(100L, 1L, "Ethereum", true),
    BSC(5600L, 56L, "BNB Chain", true),
    ARBITRUM(4216100L, 42161L, "Arbitrum One", true),
    OPTIMISM(1000L, 10L, "Optimism", true),
    LINEA(5914400L, 59144L, "Linea", true),
    ZKSYNC(32400L, 324L, "zkSync Era", true),
    POLYGON(13700L, 137L, "Polygon", true),
    BASE(845300L, 8453L, "Base", true),
    BLAST(8145700L, 81457L, "Blast", true),
    AVAX(4311400L, 43114L, "Avalanche C", true),
    BOB(6080800L, 60808L, "BOB", true),
    SCROLL(53435200L, 534352L, "Scroll", true),
    OP_BNB(20400L, 204L, "opBNB", true),
    B3(833300L, 8333L, "B3 Mainnet", true),
    WORLD_CHAIN(48000L, 480L, "World Chain", true),
    DUCK_CHAIN(554500L, 5545L, "DuckChain Mainnet", true),
    AI_LAYER(264900L, 2649L, "AILayer", true),
    BIT_LAYER(20090100L, 200901L, "Bitlayer", true),
    MERLINE_CHAIN(420000L, 4200L, "Merlin Chain", true),
    CORN(2100000000L, 21000000L, "Corn", true),
    B2_Network(22300L, 223L, "B² Network", true),
    GRAVITY_ALPHA(162500L, 1625L, "Gravity Alpha", true),
    NEO_X(4776300L, 47763L, "Neo X Mainnet", true),
    SEI_EVM(132900L, 1329L, "Sei EVM", true),
    LORENZO(832900L, 8329L, "Lorenzo", true),
    PROM(22700L, 227L, "Prom", true),
    DUCK_CHAIN_TEST(20210500L, 202105L, "DuckChain Testnet", true),
    STORY_ODYSSEY_TEST(151600L, 1516L, "Story Odyssey Testnet", true),
    STORY_MAINNET(151400L, 1514L, "Story Mainnet", true),
    BERACHAIN_BARTIO(8009400L, 80094L, "Berachain Mainnet", true),
    BERACHAIN_BARTIO_TEST(8008400L, 80084L, "Berachain bArtio Testnet", true),
    BOTANIX_TEST(363600L, 3636L, "Botanix Testnet", true),
    TAC_TEST(239000L, 2390L, "TAC Testnet", true),
    BTC(0L, 0L, "Bitcoin", false),
    SOL(50100L, 501L, "Solana", false),
    TON(110000L, 1100L, "TON", false),
    TRON(1948400L, 19484L, "Tron", false),
    SUI(78400L, 784L, "SUI", false),
    TON_TEST(110100L, 1101L, "TON Testnet", false),
    DOGE(300L, 3L, "Dogecoin", false),
    COSMOS(400L, 4L, "Cosmos Hub", false),
    ;

    private final Long chainIndex;
    private final Long chainId;
    private final String chainName;
    private final Boolean isEVM;

    ChainEnum(final Long chainIndex, final Long chainId, final String chainName, final Boolean isEVM) {
        this.chainIndex = chainIndex;
        this.chainId = chainId;
        this.chainName = chainName;
        this.isEVM = isEVM;
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
