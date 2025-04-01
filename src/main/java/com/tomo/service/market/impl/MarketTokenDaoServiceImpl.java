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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public Map<String, MarketTokenDetailInfo> queryFromCache(List<MarketTokenReq> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new HashMap<>();
        }

        List<String> tokenKeys = list.stream().map(e -> ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress())).toList();

        List<MarketTokenDetailInfo> detailInfos = redisClient.hgetAll(Constants.MARKET_TOKEN_DETAIL_REDIS_KEY, tokenKeys, MarketTokenDetailInfo.class);
        if (CollectionUtils.isEmpty(detailInfos)) {
            return new HashMap<>();
        }

        return detailInfos.stream().collect(Collectors.toMap(
                info -> ChainUtil.getTokenKey(info.getChainIndex(), info.getAddress()),
                info -> info
        ));

    }

    @Override
    public Map<String, MarketTokenDetailInfo> queryFromDb(List<MarketTokenReq> list) {
        List<MarketTokenBaseInfo> baseInfoList = queryBaseInfo(list);
        if (CollectionUtils.isEmpty(baseInfoList)) {
            return new HashMap<>();
        }

        List<Long> coinIdList = baseInfoList.stream().map(MarketTokenBaseInfo::getCoinId).toList();
        List<MarketTokenPrice> priceList = marketTokenPriceMapper.queryByCoinIds(coinIdList);
        Map<Long, MarketTokenPrice> priceMap = priceList.stream().collect(Collectors.toMap(MarketTokenPrice::getCoinId, price -> price));

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

        List<MarketTokenInfo> tokenInfos = marketTokenInfoMapper.queryTokenList(list);
        if (CollectionUtils.isEmpty(tokenInfos)) {
            return List.of();
        }

        List<Long> coinIds = tokenInfos.stream().map(MarketTokenInfo::getId).toList();

        List<MarketTokenBaseInfo> resultList = new ArrayList<>();
        List<MarketTokenCategory> categoryList = marketTokenCategoryMapper.queryByCoinIds(coinIds);
        tokenInfos.forEach(tokenInfo -> {
            List<MarketTokenCategory> categorys = categoryList.stream().filter(category1 -> category1.getCoinId().equals(tokenInfo.getId())).toList();
            MarketTokenBaseInfo baseInfo = new MarketTokenBaseInfo();
            baseInfo.setCoinId(tokenInfo.getId());
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
