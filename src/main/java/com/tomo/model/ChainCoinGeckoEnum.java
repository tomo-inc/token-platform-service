package com.tomo.model;

import lombok.Getter;

@Getter
public enum ChainCoinGeckoEnum {
    ETH(1L,CoinGeckoEnum.ETHEREUM,ChainEnum.ETH),
    BSC(56L,CoinGeckoEnum.BNB_SMART_CHAIN,ChainEnum.BSC),
    ARBITRUM(42161L,CoinGeckoEnum.ARBITRUM_ONE,ChainEnum.ARBITRUM),
    OPTIMISM(10L,CoinGeckoEnum.OPTIMISM,ChainEnum.OPTIMISM),
    LINEA(59144L,CoinGeckoEnum.LINEA,ChainEnum.LINEA),
    ZKSYNC(324L,CoinGeckoEnum.ZKSYNC,ChainEnum.ZKSYNC),
    POLYGON(137L,CoinGeckoEnum.POLYGON_POS,ChainEnum.POLYGON),
    BASE(8453L,CoinGeckoEnum.BASE,ChainEnum.BASE),
    BLAST(81457L,CoinGeckoEnum.BLAST,ChainEnum.BLAST),
    AVAX(43114L,CoinGeckoEnum.AVALANCHE,ChainEnum.AVAX),
    BOB(60808L,CoinGeckoEnum.BOB_NETWORK,ChainEnum.BOB),
    SCROLL(534352L,CoinGeckoEnum.SCROLL,ChainEnum.SCROLL),
    OP_BNB(204L,CoinGeckoEnum.OPBNB,ChainEnum.OP_BNB),
//    B3(8333L,CoinGeckoEnum.),
    WORLD_CHAIN(480L,CoinGeckoEnum.WORLD_CHAIN,ChainEnum.WORLD_CHAIN),
    DUCK_CHAIN(5545L,CoinGeckoEnum.DUCKCHAIN,ChainEnum.DUCK_CHAIN),
//    AI_LAYER(2649L,CoinGeckoEnum.ai),
    BIT_LAYER(200901L,CoinGeckoEnum.BITLAYER,ChainEnum.BIT_LAYER),
    MERLINE_CHAIN(4200L,CoinGeckoEnum.MERLIN_CHAIN,ChainEnum.MERLINE_CHAIN),
//    CORN(21000000L,CoinGeckoEnum.COR),
//    B2_Network(223L,CoinGeckoEnum.B),
    GRAVITY_ALPHA(1625L,CoinGeckoEnum.GRAVITY,ChainEnum.GRAVITY_ALPHA),
    NEO_X(47763L,CoinGeckoEnum.NEO,ChainEnum.NEO_X),
    SEI_EVM(1329L,CoinGeckoEnum.SEI_V2,ChainEnum.SEI_EVM),
//    LORENZO(8329L,CoinGeckoEnum.LO),
//    PROM(227L,CoinGeckoEnum.PROM),
//    DUCK_CHAIN_TEST(202105L,CoinGeckoEnum.DUCKCHAI),
//    STORY_ODYSSEY_TEST(1516L,CoinGeckoEnum.STORY),
//    BERACHAIN_BARTIO_TEST(80084L,"Berachain bArtio Testnet"),
//    BOTANIX_TEST(3636L,"Botanix Testnet"),
//    TAC_TEST(2390L,"TAC Testnet"),
    BTC(0L,CoinGeckoEnum.BITCOIN,ChainEnum.BTC),
    SOL( 501L,CoinGeckoEnum.SOLANA,ChainEnum.SOL),
    TON(1100L,CoinGeckoEnum.TON,ChainEnum.TON),
    TRON(19484L,CoinGeckoEnum.TRON,ChainEnum.TRON),
    SUI(784L,CoinGeckoEnum.SUI,ChainEnum.SUI),
//    TON_TEST(1101L,"TON Testnet"),
    DOGE(3L,CoinGeckoEnum.DOGECHAIN,ChainEnum.DOGE),
    COSMOS(4L,CoinGeckoEnum.COSMOS,ChainEnum.COSMOS),
    BERACHAIN(80094L,CoinGeckoEnum.BERACHAIN,ChainEnum.BERACHAIN_BARTIO),
    STORY_MAINNET(1514L,CoinGeckoEnum.STORY_MAINNET,ChainEnum.STORY_MAINNET),
    ;
    private final Long chainId;
    private final CoinGeckoEnum coinGeckoEnum;
    private final ChainEnum chainEnum;

    ChainCoinGeckoEnum(Long chainId, CoinGeckoEnum coinGeckoEnum, ChainEnum chainEnum) {
        this.chainId = chainId;
        this.coinGeckoEnum = coinGeckoEnum;
        this.chainEnum = chainEnum;
    }
}
