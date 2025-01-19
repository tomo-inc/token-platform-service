package com.tomo.service.category.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomo.feign.OKXClient;
import com.tomo.model.dto.RedisData;
import com.tomo.model.dto.TokenRankDTO;
import com.tomo.model.resp.OKXDatumResp;
import com.tomo.model.resp.OKXResult;
import com.tomo.service.RedisClient;
import com.tomo.service.category.TokenRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TokenRankServiceImpl implements TokenRankService {
    public static final String OKX_TRENDING_MAP_KEY = "OKX-TRENDING";

    @Autowired
    OKXClient okxClient;
    @Autowired
    RedisClient redisClient;

    long maxGap = 30 * 60 * 1000;

    @Override
    public List<TokenRankDTO> getOKXTrending(String chainId) {
        RedisData data = redisClient.hget(OKX_TRENDING_MAP_KEY, chainId, RedisData.class);
        if (data == null || System.currentTimeMillis() - data.getSaveTime() > maxGap) {
            return updateOKXTrending(chainId);
        }
        return data.getData(new TypeReference<List<TokenRankDTO>>(){});
    }


    public List<TokenRankDTO> updateOKXTrending(String chainId) {
        OKXResult<OKXDatumResp> all = okxClient.listTrendingTokens(chainId, System.currentTimeMillis());
        OKXDatumResp data = all.getData();
        List<TokenRankDTO> list = data.getMarketListsTokenInfos().stream().map(marketInfo -> {
            try {
                TokenRankDTO tokenRankDTO = new TokenRankDTO();
//                tokenRankDTO.setChainId(ChainUtil.getOkxChainInfoMap().getOrDefault(Long.valueOf(marketInfo.getChainId()), ChainInfoEnum.UNKNOWN).getChainId());
                tokenRankDTO.setAddress(marketInfo.getTokenContractAddress());
                tokenRankDTO.setName(marketInfo.getTokenSymbol());
                tokenRankDTO.setSymbol(marketInfo.getTokenSymbol());
                tokenRankDTO.setImageUrl(marketInfo.getTokenLogoUrl());
                tokenRankDTO.setSource("OKX");
                tokenRankDTO.setTag(OKX_TRENDING_MAP_KEY);
                tokenRankDTO.setRealPrice(marketInfo.getPrice());
                tokenRankDTO.setVolume24h(marketInfo.getVolume());
                tokenRankDTO.setChange24h(marketInfo.getChange24H());
                tokenRankDTO.setMarketCap(marketInfo.getMarketCap());
                return tokenRankDTO;
            }catch (Exception e){
                log.error("updateOKXTrending error", e);
            }
            return null;
        }).toList();
        redisClient.hset(OKX_TRENDING_MAP_KEY, chainId, RedisData.build(list));
        return list;
    }

}
