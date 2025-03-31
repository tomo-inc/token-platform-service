package com.tomo.service.market.impl;

import com.tomo.mapper.MarketTokenCategoryMapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.mapper.MarketTokenPriceMapper;
import com.tomo.model.market.MarketTokenCategory;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.market.MarketTokenPrice;
import com.tomo.model.req.MarketTokenCategoryReq;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.model.resp.MarketTokenDetailInfo;
import com.tomo.model.resp.MarketTokenHistory;
import com.tomo.service.market.MarketTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarketTokenServiceImpl implements MarketTokenService {

    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;

    @Autowired
    private MarketTokenPriceMapper marketTokenPriceMapper;

    @Autowired
    private MarketTokenCategoryMapper marketTokenCategoryMapper;

//    @Autowired
//    private MarketTokenCacheService marketTokenCacheService;

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public List<MarketTokenBaseInfo> queryBaseInfoList(List<MarketTokenReq> list) {

//        marketTokenCacheService.cacheTokenList(list);
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

    @Override
    public List<MarketTokenDetailInfo> details(List<MarketTokenReq> list) {
        List<MarketTokenBaseInfo> baseInfoList = queryBaseInfoList(list);
        if (CollectionUtils.isEmpty(baseInfoList)) {
            return List.of();
        }

        List<Long> coinIdList = baseInfoList.stream().map(MarketTokenBaseInfo::getCoinId).toList();
        List<MarketTokenPrice> priceList = marketTokenPriceMapper.queryByCoinIds(coinIdList);
        Map<Long, MarketTokenPrice> priceMap = priceList.stream().collect(Collectors.toMap(MarketTokenPrice::getCoinId, price -> price));

        List<MarketTokenDetailInfo> resultList = new ArrayList<>();
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
            resultList.add(detailInfo);

        }

        return resultList;
    }

    @Override
    public List<MarketTokenDetailInfo> category(MarketTokenCategoryReq req) {
        int pageNum = req.getPageNum() == null ? DEFAULT_PAGE_NUM : req.getPageNum();
        int pageSize = req.getPageSize() == null? DEFAULT_PAGE_SIZE : req.getPageSize();
        req.setPageNum(pageNum - 1);
        req.setPageSize(pageSize);

        List<MarketTokenCategory> categoryList = marketTokenCategoryMapper.pageQuery(req);
        if (CollectionUtils.isEmpty(categoryList)) {
            return List.of();
        }
        List<MarketTokenReq> tokenReqList = categoryList.stream().map(e -> {
            MarketTokenReq tokenReq = new MarketTokenReq();
            tokenReq.setChainIndex(e.getChainIndex());
            tokenReq.setAddress(e.getAddress());
            return tokenReq;
        }).toList();

        List<MarketTokenInfo> baseInfoList = marketTokenInfoMapper.queryTokenList(tokenReqList);
        Map<Long, MarketTokenInfo> baseMap = baseInfoList.stream().collect(Collectors.toMap(MarketTokenInfo::getId, e -> e));
        List<MarketTokenPrice> priceList = marketTokenPriceMapper.queryByCoinIds(baseInfoList.stream().map(MarketTokenInfo::getId).toList());
        Map<Long, MarketTokenPrice> priceMap = priceList.stream().collect(Collectors.toMap(MarketTokenPrice::getCoinId, e -> e));

        List<MarketTokenDetailInfo> resultList = new ArrayList<>();
        categoryList.forEach(e -> {
            MarketTokenInfo baseInfo = baseMap.get(e.getCoinId());
            MarketTokenPrice price = priceMap.get(e.getCoinId());
            MarketTokenDetailInfo detailInfo = new MarketTokenDetailInfo();
            detailInfo.setCoinId(baseInfo.getId());
            detailInfo.setChainIndex(baseInfo.getChainIndex());
            detailInfo.setAddress(baseInfo.getAddress());
            detailInfo.setIsNative(baseInfo.getIsNative());
            detailInfo.setName(baseInfo.getName());
            detailInfo.setSymbol(baseInfo.getSymbol());
            detailInfo.setImageUrl(baseInfo.getImageUrl());
            detailInfo.setDecimals(baseInfo.getDecimals());
            detailInfo.setTotalSupply(baseInfo.getTotalSupply() == null ? "" : baseInfo.getTotalSupply().toPlainString());
            detailInfo.setCategory(Map.of(e.getCategory(), e.getTags()));
            detailInfo.setRiskLevel(baseInfo.getRiskLevel());
            detailInfo.setSocial(baseInfo.getSocial());
            if (price != null) {
                detailInfo.setRealPrice(price.getRealPrice().toPlainString());
                detailInfo.setChange24h(price.getChange24h().toPlainString());
                detailInfo.setVolume24h(price.getVolume24h().toPlainString());
                detailInfo.setMarketCap(price.getMarketCap().toPlainString());
                detailInfo.setFdvUsd(price.getFdvUsd().toPlainString());
                detailInfo.setLiquidityUsd(price.getLiquidityUsd().toPlainString());
            }

            resultList.add(detailInfo);
        });

        return resultList;
    }

    @Override
    public List<MarketTokenHistory> history(Long chainIndex, String address) {
        return List.of();
    }
}
