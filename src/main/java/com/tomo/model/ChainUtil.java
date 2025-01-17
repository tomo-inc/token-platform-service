package com.tomo.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChainUtil {
    public static void main(String[] args) {

    }
    @Getter
    public static List<ChainInfoEnum> allChainInfo = Arrays.stream(ChainInfoEnum.values()).collect(Collectors.toList());

    @Getter
    public static Map<Long, ChainInfoEnum> chainInfoMap = Arrays.stream(ChainInfoEnum.values()).collect(Collectors.toMap(ChainInfoEnum::getChainId, Function.identity()));

    @Getter
    public static Map<Long, ChainInfoEnum> okxChainInfoMap = Arrays.stream(ChainInfoEnum.values()).collect(Collectors.toMap(ChainInfoEnum::getOkxId, Function.identity()));

    @Getter
    public static Map<String, ChainInfoEnum> coinGeckoChainInfoMap = Arrays.stream(ChainInfoEnum.values()).collect(Collectors.toMap(ChainInfoEnum::getCoingeckoChainName, Function.identity()));


    public static String getCommonKey(Long chainId,String address) {
        return chainId + "-" + address;
    }
}
