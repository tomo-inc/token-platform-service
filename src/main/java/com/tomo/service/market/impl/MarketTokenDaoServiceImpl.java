package com.tomo.service.market.impl;

import com.tomo.mapper.MarketTokenCategoryMapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.mapper.MarketTokenPriceMapper;
import com.tomo.model.ChainUtil;
import com.tomo.model.Constants;
import com.tomo.model.market.MarketTokenCategory;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.market.MarketTokenPrice;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.model.resp.MarketTokenDetailInfo;
import com.tomo.service.RedisClient;
import com.tomo.service.market.MarketTokenDaoService;
import com.tomo.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MarketTokenDaoServiceImpl implements MarketTokenDaoService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;
    @Autowired
    private MarketTokenCategoryMapper marketTokenCategoryMapper;
    @Autowired
    private MarketTokenPriceMapper marketTokenPriceMapper;

    @Value("${market.token.cache.time.seconds:3600}")
    private Long tokenCacheTimeSeconds;

    @Override
    public Map<String, MarketTokenDetailInfo> queryFromCache(List<MarketTokenReq> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }

        List<MarketTokenDetailInfo> detailInfos = new ArrayList<>();

        List<String> nativeKeys = list.stream().filter(e -> StringUtils.isBlank(e.getAddress())).map(e -> ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress())).toList();
        List<String> tokenKeys = list.stream().filter(e -> StringUtils.isNoneBlank(e.getAddress())).map(e -> ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress())).toList();
        if (!CollectionUtils.isEmpty(nativeKeys)) {
            List<MarketTokenDetailInfo> nativeInfos = redisClient.hgetAll(Constants.MARKET_NATIVE_TOKEN_DETAIL_KEY, nativeKeys, MarketTokenDetailInfo.class);
            if (!CollectionUtils.isEmpty(nativeInfos)) {
                detailInfos.addAll(nativeInfos);
            }
        }

        if (!CollectionUtils.isEmpty(tokenKeys)) {
            List<String> cacheKeys = tokenKeys.stream().map(e -> String.format(Constants.MARKET_CONTRACT_TOKEN_DETAIL_KEY_PREFIX, e)).toList();
            List<String> jsonList = redisClient.multiGet(cacheKeys);
            if (!CollectionUtils.isEmpty(jsonList)) {
                List<MarketTokenDetailInfo> contractTokens = jsonList.stream().filter(Objects::nonNull).map(e -> JsonUtil.parseObject(e, MarketTokenDetailInfo.class)).toList();
                if (!CollectionUtils.isEmpty(contractTokens)) {
                    detailInfos.addAll(contractTokens);
                }
            }
        }

        return detailInfos.stream().collect(Collectors.toMap(
                info -> ChainUtil.getTokenKey(info.getChainIndex(), info.getAddress()),
                info -> info
        ));

    }

    @Override
    public void saveCache(List<MarketTokenDetailInfo> list) {
        Map<Boolean, List<MarketTokenDetailInfo>> map = list.stream().collect(Collectors.groupingBy(MarketTokenDetailInfo::getIsNative));
        List<MarketTokenDetailInfo> nativeList = map.get(true);
        List<MarketTokenDetailInfo> contractList = map.get(false);
        if (!CollectionUtils.isEmpty(nativeList)) {
            redisClient.hsetAll(Constants.MARKET_NATIVE_TOKEN_DETAIL_KEY, nativeList.stream().collect(Collectors.toMap(
                    info -> ChainUtil.getTokenKey(info.getChainIndex(), info.getAddress()),
                    info -> info
            )));
        }
        if (!CollectionUtils.isEmpty(contractList)) {
            Map<String, String> cacheMap = contractList.stream().collect(Collectors.toMap(e -> String.format(Constants.MARKET_CONTRACT_TOKEN_DETAIL_KEY_PREFIX, ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress())), JsonUtil::toJson));
            redisClient.multiSet(cacheMap, tokenCacheTimeSeconds);
        }


    }

    @Override
    public Map<String, MarketTokenDetailInfo> queryFromDb(List<MarketTokenReq> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }
        List<MarketTokenBaseInfo> baseInfoList = queryBaseInfo(list);
        if (CollectionUtils.isEmpty(baseInfoList)) {
            return new HashMap<>();
        }

        List<String> coinIdList = baseInfoList.stream().map(MarketTokenBaseInfo::getCoinId).toList();
        List<MarketTokenPrice> priceList = marketTokenPriceMapper.queryByCoinIds(coinIdList);
        Map<String, MarketTokenPrice> priceMap = priceList.stream().collect(Collectors.toMap(MarketTokenPrice::getCoinId, price -> price));

        Map<String, MarketTokenDetailInfo> resultMap = new HashMap<>();
        for (MarketTokenBaseInfo info : baseInfoList) {
            MarketTokenPrice price = priceMap.get(info.getCoinId());

            MarketTokenDetailInfo detailInfo = new MarketTokenDetailInfo();
            detailInfo.setCoinId(info.getCoinId());
            detailInfo.setChainIndex(info.getChainIndex());
            detailInfo.setAddress(info.getAddress());
            detailInfo.setIsNative(info.getIsNative());
            detailInfo.setName(info.getName());
            detailInfo.setSymbol(info.getSymbol());
            detailInfo.setImageUrl(info.getImageUrl());
            detailInfo.setDecimals(info.getDecimals());
            detailInfo.setTotalSupply(info.getTotalSupply());
            detailInfo.setCategory(info.getCategory());
            detailInfo.setRiskLevel(info.getRiskLevel());
            detailInfo.setSocial(info.getSocial());
            if (price != null) {
                detailInfo.setRealPrice(price.getRealPrice().toPlainString());
                detailInfo.setChange24h(price.getChange24h().toPlainString());
                detailInfo.setVolume24h(price.getVolume24h().toPlainString());
                detailInfo.setMarketCap(price.getMarketCap().toPlainString());
                detailInfo.setFdvUsd(price.getFdvUsd().toPlainString());
                detailInfo.setLiquidityUsd(price.getLiquidityUsd().toPlainString());
            }
            resultMap.put(ChainUtil.getTokenKey(info.getChainIndex(), info.getAddress()), detailInfo);

        }

        return resultMap;

    }


    @Override
    public List<MarketTokenBaseInfo> queryBaseInfo(List<MarketTokenReq> list) {

        List<String> coinIds = list.stream().map(e -> ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress())).distinct().toList();
        List<MarketTokenInfo> tokenInfos = marketTokenInfoMapper.queryByCoinIds(coinIds);
        if (CollectionUtils.isEmpty(tokenInfos)) {
            return List.of();
        }

        List<MarketTokenBaseInfo> resultList = new ArrayList<>();
        List<MarketTokenCategory> categoryList = marketTokenCategoryMapper.queryByCoinIds(coinIds);
        tokenInfos.forEach(tokenInfo -> {
            List<MarketTokenCategory> categorys = categoryList.stream().filter(category1 -> category1.getCoinId().equals(tokenInfo.getId())).toList();
            MarketTokenBaseInfo baseInfo = new MarketTokenBaseInfo();
            baseInfo.setCoinId(tokenInfo.getCoinId());
            baseInfo.setChainIndex(tokenInfo.getChainIndex());
            baseInfo.setAddress(tokenInfo.getAddress());
            baseInfo.setIsNative(tokenInfo.getIsNative());
            baseInfo.setName(tokenInfo.getName());
            baseInfo.setSymbol(tokenInfo.getSymbol());
            baseInfo.setImageUrl(tokenInfo.getImageUrl());
            baseInfo.setDecimals(tokenInfo.getDecimals());
            baseInfo.setTotalSupply(tokenInfo.getTotalSupply() == null ? "" : tokenInfo.getTotalSupply().toPlainString());

            if (!CollectionUtils.isEmpty(categorys)) {
                Map<String, List<String>> categoryMap = categorys.stream()
                        .collect(Collectors.toMap(MarketTokenCategory::getCategory, MarketTokenCategory::getTags));
                baseInfo.setCategory(categoryMap);
            }
            baseInfo.setRiskLevel(tokenInfo.getRiskLevel());
            baseInfo.setSocial(tokenInfo.getSocial());
            resultList.add(baseInfo);

        });
        return resultList;
    }
}
