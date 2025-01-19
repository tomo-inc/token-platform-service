package com.tomo.model;

import lombok.Getter;
import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChainUtil {
    //todo
//    @Getter
//    public static Map<Long, ChainInfoEnum> okxChainInfoMap = Arrays.stream(ChainInfoEnum.values()).collect(Collectors.toMap(ChainInfoEnum::getOkxId, Function.identity()));

    // platformid -> enum
    @Getter
    public static Map<String, Pair<CoinGeckoEnum,ChainEnum>> coinGeckoChainInfoMap = Arrays
            .stream(ChainCoinGeckoEnum.values())
            .collect(
                    Collectors.toMap(e-> e.getCoinGeckoEnum().getPlatformChainId(),
                                    e->Pair.of( e.getCoinGeckoEnum(),e.getChainEnum())
                    )
            );

    // chainid -> ChainEnum
    public static Map<Long,ChainEnum> chainIdMap = Arrays
            .stream(ChainEnum.values())
            .collect(Collectors.toMap(ChainEnum::getChainId, Function.identity()));


    // chainId -> chain and coingecko
    @Getter
    public static Map<Long,ChainCoinGeckoEnum> chainAndCoinGeckoMap = Arrays
            .stream(ChainCoinGeckoEnum.values())
            .collect(Collectors.toMap(ChainCoinGeckoEnum::getChainId, Function.identity()));


    public static String getCommonKey(Long chainId,String address) {
        return chainId + "-" + address;
    }
}
