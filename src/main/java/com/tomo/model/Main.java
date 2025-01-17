package com.tomo.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ChainEnum[] values = ChainEnum.values();
        CoinGeckoEnum[] values1 = CoinGeckoEnum.values();

        Map<String, ChainEnum> collect = Arrays.stream(values).collect(Collectors.toMap(ChainEnum::name, Function.identity()));
        Arrays.stream(values1).forEach(e->{
            if(collect.containsKey(e.getPlatformChainId().toUpperCase())){
                System.out.println("1111111");
            }
        });
    }
}
