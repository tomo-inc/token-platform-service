package com.tomo.service;

import com.tomo.model.TokenBase;
import com.tomo.model.dto.TokenInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TokenInfoCacheService {
    public static String HASH_NAME = "token_platform_service.token_info.map_key";
    @Autowired
    RedisClient redisClient;

    public List<TokenInfoDTO> hGetAll(List<TokenBase> tokenName) {
        List<String> list = tokenName.stream().map(TokenBase::getId).toList();
        return redisClient.hgetAll(HASH_NAME,list,TokenInfoDTO.class);
    }

    public void hSetAll(List<TokenInfoDTO> price) {
        Map<String, TokenInfoDTO> infoDTOMap = price.stream()
                .collect(Collectors.toMap(TokenInfoDTO::getId, Function.identity()));
        redisClient.hsetAll(HASH_NAME,infoDTOMap);
    }
}
