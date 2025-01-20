package com.tomo.model;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChainUtil {
    //todo
//    @Getter
//    public static Map<Long, ChainInfoEnum> okxChainInfoMap = Arrays.stream(ChainInfoEnum.values()).collect(Collectors.toMap(ChainInfoEnum::getOkxId, Function.identity()));

    // platformid -> enum
    @Getter
    public static Map<String, ChainCoinGeckoEnum> coinGeckoChainInfoMap = Arrays
            .stream(ChainCoinGeckoEnum.values())
            .collect(
                    Collectors.toMap(e-> e.getCoinGeckoEnum().getPlatformChainId(),
                                    Function.identity()
                    )
            );

    // coinId -> enum
    @Getter
    public static HashMap<String, List<ChainCoinGeckoEnum>> nativeIdToEnum = new HashMap<>();
    static {
        Arrays.stream(ChainCoinGeckoEnum.values())
                .forEach(e -> {
                    String coinId = e.getCoinGeckoEnum().getCoinId();
                    if (nativeIdToEnum.containsKey(coinId)) {
                        nativeIdToEnum.get(coinId).add(e);
                    } else {
                        List<ChainCoinGeckoEnum> list = new ArrayList<>();
                        list.add(e);
                        nativeIdToEnum.put(coinId, list);
                    }
                });
    }



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
        if (StringUtils.hasText(address)) {
            return chainId + "-" + address;
        }else {
            return chainId.toString();
        }
    }
}
