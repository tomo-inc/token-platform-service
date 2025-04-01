package com.tomo.service.market.impl;

import com.tomo.feign.CoinGeckoClient;
import com.tomo.mapper.MarketTokenCategoryMapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.mapper.MarketTokenPriceMapper;
import com.tomo.model.*;
import com.tomo.model.market.MarketTokenCategory;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.market.MarketTokenPrice;
import com.tomo.model.market.SocialInfo;
import com.tomo.model.req.MarketTokenCategoryReq;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.*;
import com.tomo.service.RedisClient;
import com.tomo.service.market.MarketTokenDaoService;
import com.tomo.service.market.MarketTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MarketTokenServiceImpl implements MarketTokenService {

    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;

    @Autowired
    private MarketTokenPriceMapper marketTokenPriceMapper;

    @Autowired
    private MarketTokenCategoryMapper marketTokenCategoryMapper;

    @Autowired
    private MarketTokenDaoService marketTokenDaoService;

    @Autowired
    private CoinGeckoClient coinGeckoClient;

    @Autowired
    private RedisClient redisClient;

    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public List<MarketTokenBaseInfo> queryBaseInfoList(List<MarketTokenReq> list) {

        return marketTokenDaoService.queryBaseInfo(list);
    }

    @Override
    public List<MarketTokenDetailInfo> details(List<MarketTokenReq> list) {

        Map<String, MarketTokenDetailInfo> cacheMap = marketTokenDaoService.queryFromCache(list);
        List<MarketTokenReq> tokenReqList = list.stream().filter(e -> {
            String key = ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress());
            return !cacheMap.containsKey(key);
        }).toList();
        Map<String, MarketTokenDetailInfo> dbMap = marketTokenDaoService.queryFromDb(tokenReqList);

        // TODO coinGecko token 需要查询最新币价

        List<MarketTokenReq> newToken = tokenReqList.stream().filter(e -> {
            String key = ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress());
            return !dbMap.containsKey(key);
        }).toList();

        List<MarketTokenDetailInfo> newList = addTokens(newToken);

        List<MarketTokenDetailInfo> mergedList = new ArrayList<>();
        mergedList.addAll(cacheMap.values());
        mergedList.addAll(dbMap.values());
        mergedList.addAll(newList);

        return mergedList;
    }

    public List<MarketTokenDetailInfo> addTokens(List<MarketTokenReq> list) {
        if (CollectionUtils.isEmpty(list)) {
            return List.of();
        }

        Map<Long, List<MarketTokenReq>> groupByChainMap = list.stream()
                .collect(Collectors.groupingBy(MarketTokenReq::getChainIndex));
        List<MarketTokenDetailInfo> resultList = new ArrayList<>();

        for (Map.Entry<Long, List<MarketTokenReq>> entry : groupByChainMap.entrySet()) {
            Long chainIndex = entry.getKey();
            ChainEnum chainEnum = ChainEnum.getChanByIndex(chainIndex);
            List<MarketTokenReq> chainTokens = entry.getValue();
            ChainCoinGeckoEnum chainInfoEnum = ChainUtil.getChainAndCoinGeckoMap().get(chainEnum.getChainId());
            if (chainInfoEnum == null) {
                log.info("MarketTokenServiceImpl#addTokens chainIndex {} not found", chainIndex);
                continue;
            }

            List<MarketTokenReq> nativeTokens = chainTokens.stream()
                    .filter(token -> !StringUtils.hasLength(token.getAddress()))
                    .toList();
            List<MarketTokenReq> contractTokens = chainTokens.stream()
                    .filter(token -> StringUtils.hasLength(token.getAddress()))
                    .toList();

            if (!CollectionUtils.isEmpty(nativeTokens)) {
                handleNativeTokens(chainIndex, chainInfoEnum, nativeTokens, resultList);
            }

            if (!CollectionUtils.isEmpty(contractTokens)) {
                handleContractTokens(chainIndex, chainInfoEnum, contractTokens, resultList);
            }
        }

        return resultList;
    }

    private void handleNativeTokens(Long chainIndex, ChainCoinGeckoEnum chainInfoEnum,
                                    List<MarketTokenReq> nativeTokens, List<MarketTokenDetailInfo> resultList) {
        String coinId = chainInfoEnum.getCoinGeckoEnum().getCoinId();
        CoinInfoResp nativeTokenInfo = coinGeckoClient.getPlatformCoinInfo(coinId);

        if (nativeTokenInfo != null) {
            // 创建代币基本信息列表
            List<MarketTokenInfo> tokenInfoList = nativeTokens.stream()
                    .map(token -> createMarketTokenInfo(chainIndex, nativeTokenInfo))
                    .toList();

            // 批量保存代币基本信息
            marketTokenInfoMapper.batchInsert(tokenInfoList);

            // 创建代币价格信息列表
            List<MarketTokenPrice> tokenPriceList = tokenInfoList.stream()
                    .map(tokenInfo -> createMarketTokenPrice(chainIndex, nativeTokenInfo))
                    .toList();

            // 批量保存代币价格信息
            marketTokenPriceMapper.batchInsert(tokenPriceList);

            // 创建并保存详细信息到缓存
            List<MarketTokenDetailInfo> detailInfoList = tokenInfoList.stream()
                    .map(tokenInfo -> {
                        MarketTokenPrice tokenPrice = tokenPriceList.stream()
                                .filter(price -> price.getCoinId().equals(tokenInfo.getCoinId()))
                                .findFirst()
                                .orElse(null);
                        return createMarketTokenDetailInfo(chainIndex, tokenInfo, tokenPrice);
                    })
                    .toList();

            // 批量保存到缓存
            detailInfoList.forEach(detailInfo ->
                    redisClient.hset(Constants.MARKET_TOKEN_DETAIL_REDIS_KEY,
                            ChainUtil.getTokenKey(chainIndex, ""), detailInfo));

            resultList.addAll(detailInfoList);
        }
    }

    private void handleContractTokens(Long chainIndex, ChainCoinGeckoEnum chainInfoEnum,
                                      List<MarketTokenReq> contractTokens, List<MarketTokenDetailInfo> resultList) {
        List<String> addresses = contractTokens.stream()
                .map(MarketTokenReq::getAddress)
                .filter(StringUtils::hasLength)
                .toList();

        DexTokenResp dexTokenResp = coinGeckoClient.batchGetTokenInfo(
                chainInfoEnum.getCoinGeckoEnum().getNetworkId(), String.join(",", addresses));

        if (dexTokenResp == null || CollectionUtils.isEmpty(dexTokenResp.getData())) {
            return;
        }

        List<MarketTokenInfo> tokenInfoList = dexTokenResp.getData().stream()
                .map(dexData -> createMarketTokenInfo(chainIndex, dexData.getAttributes()))
                .toList();
        marketTokenInfoMapper.batchInsert(tokenInfoList);

        List<MarketTokenPrice> tokenPriceList = new ArrayList<>();
        List<MarketTokenDetailInfo> detailInfoList = new ArrayList<>();

        for (int i = 0; i < tokenInfoList.size(); i++) {
            MarketTokenInfo tokenInfo = tokenInfoList.get(i);
            DexTokenResp.DexData.Attributes attributes = dexTokenResp.getData().get(i).getAttributes();

            MarketTokenPrice tokenPrice = createMarketTokenPrice(chainIndex, attributes);
            tokenPriceList.add(tokenPrice);

            MarketTokenDetailInfo detailInfo = createMarketTokenDetailInfo(chainIndex, tokenInfo, tokenPrice);
            detailInfoList.add(detailInfo);
        }

        marketTokenPriceMapper.batchInsert(tokenPriceList);
        detailInfoList.forEach(detailInfo ->
                redisClient.hset(Constants.MARKET_TOKEN_DETAIL_REDIS_KEY,
                        ChainUtil.getTokenKey(chainIndex, detailInfo.getAddress()), detailInfo));
        resultList.addAll(detailInfoList);
    }

    private MarketTokenInfo createMarketTokenInfo(Long chainIndex, CoinInfoResp nativeTokenInfo) {
        MarketTokenInfo tokenInfo = new MarketTokenInfo();

        tokenInfo.setCoinId(ChainUtil.getTokenKey(chainIndex, ""));
        tokenInfo.setChainIndex(chainIndex);
        tokenInfo.setAddress("");
        tokenInfo.setIsNative(true);
        tokenInfo.setName(nativeTokenInfo.getName());
        tokenInfo.setSymbol(nativeTokenInfo.getSymbol());
        tokenInfo.setImageUrl(nativeTokenInfo.getImage().getSmall());
//        tokenInfo.setDecimals(nativeTokenInfo.getDetailPlatforms().);
        CoinInfoResp.Links links = nativeTokenInfo.getLinks();
        SocialInfo socialInfo = new SocialInfo();
        if (!CollectionUtils.isEmpty(nativeTokenInfo.getLinks().getHomepage()) && StringUtils.hasLength(nativeTokenInfo.getLinks().getHomepage().get(0))) {
            socialInfo.setWebsiteUrl(nativeTokenInfo.getLinks().getHomepage().get(0));
        }
        if (StringUtils.hasLength(nativeTokenInfo.getLinks().getTwitterScreenName())) {
            socialInfo.setTwitterUrl(String.format("https://x.com/%s", nativeTokenInfo.getLinks().getTwitterScreenName()));
        }
        if (StringUtils.hasLength(nativeTokenInfo.getLinks().getTelegramChannelIdentifier())) {
            socialInfo.setTelegramUrl(String.format("https://t.me/%s", nativeTokenInfo.getLinks().getTelegramChannelIdentifier()));
        }
        tokenInfo.setSocial(socialInfo);
        tokenInfo.setTotalSupply(nativeTokenInfo.getMarketData() != null ? nativeTokenInfo.getMarketData().getTotalSupply() : null);
        tokenInfo.setForceSafe(false);
        tokenInfo.setRiskLevel(RiskLevel.NONE.getCode());
        tokenInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        tokenInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return tokenInfo;
    }

    private MarketTokenInfo createMarketTokenInfo(Long chainIndex, DexTokenResp.DexData.Attributes attributes) {
        MarketTokenInfo tokenInfo = new MarketTokenInfo();
        tokenInfo.setChainIndex(chainIndex);
        tokenInfo.setAddress(attributes.getAddress());
        tokenInfo.setIsNative(false);
        tokenInfo.setName(attributes.getName());
        tokenInfo.setSymbol(attributes.getSymbol());
        tokenInfo.setImageUrl(attributes.getImageUrl());
        tokenInfo.setDecimals(attributes.getDecimals());
        tokenInfo.setTotalSupply(attributes.getTotalSupply());
        tokenInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        tokenInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return tokenInfo;
    }

    private MarketTokenPrice createMarketTokenPrice(Long chainIndex, CoinInfoResp nativeTokenInfo) {
        MarketTokenPrice tokenPrice = new MarketTokenPrice();
        tokenPrice.setCoinId(ChainUtil.getTokenKey(chainIndex, ""));
        tokenPrice.setChainIndex(chainIndex);
        tokenPrice.setAddress("");
        CoinInfoResp.MarketData marketData = nativeTokenInfo.getMarketData();

        tokenPrice.setLiquidityUsd(marketData.getCirculatingSupply() == null ? null : marketData.getCirculatingSupply());
        tokenPrice.setRealPrice(marketData.getCurrentPrice() == null ? null : marketData.getCurrentPrice().getUsd());
        tokenPrice.setVolume24h(marketData.getMarketCapChange24H() == null ? null : marketData.getMarketCapChange24H());
        tokenPrice.setChange24h(marketData.getPriceChange24H() == null ? null : BigDecimal.valueOf(marketData.getPriceChange24H()));
        tokenPrice.setMarketCap(marketData.getMarketCap() == null ? null : marketData.getMarketCap().getUsd());
        tokenPrice.setFdvUsd(marketData.getFullyDilutedValuation() == null ? null : marketData.getFullyDilutedValuation().getUsd());
        tokenPrice.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        tokenPrice.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return tokenPrice;
    }

    private MarketTokenPrice createMarketTokenPrice(Long chainIndex,
                                                    DexTokenResp.DexData.Attributes attributes) {
        MarketTokenPrice tokenPrice = new MarketTokenPrice();
        tokenPrice.setCoinId(ChainUtil.getTokenKey(chainIndex, attributes.getAddress()));
        tokenPrice.setChainIndex(chainIndex);
        tokenPrice.setAddress(attributes.getAddress());
        tokenPrice.setRealPrice(attributes.getPriceUsd());
        tokenPrice.setVolume24h(attributes.getVolumeUsd() != null ? attributes.getVolumeUsd().getH24() : null);
        tokenPrice.setChange24h(attributes.getPriceChangePercentage() != null ? attributes.getPriceChangePercentage().getH24() : null);
        tokenPrice.setMarketCap(attributes.getMarketCapUsd());
        tokenPrice.setFdvUsd(attributes.getFdvUsd());
        tokenPrice.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        tokenPrice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return tokenPrice;
    }

    private MarketTokenDetailInfo createMarketTokenDetailInfo(Long chainIndex, MarketTokenInfo tokenInfo,
                                                              MarketTokenPrice tokenPrice) {
        MarketTokenDetailInfo detailInfo = new MarketTokenDetailInfo();
        detailInfo.setCoinId(tokenInfo.getCoinId());
        detailInfo.setChainIndex(chainIndex);
        detailInfo.setAddress(tokenInfo.getAddress());
        detailInfo.setIsNative(tokenInfo.getIsNative());
        detailInfo.setName(tokenInfo.getName());
        detailInfo.setSymbol(tokenInfo.getSymbol());
        detailInfo.setImageUrl(tokenInfo.getImageUrl());
        detailInfo.setDecimals(tokenInfo.getDecimals());
        detailInfo.setTotalSupply(tokenInfo.getTotalSupply() != null ? tokenInfo.getTotalSupply().toPlainString() : "");
        detailInfo.setRealPrice(tokenPrice.getRealPrice().toPlainString());
        detailInfo.setVolume24h(tokenPrice.getVolume24h() != null ? tokenPrice.getVolume24h().toPlainString() : "");
        detailInfo.setChange24h(tokenPrice.getChange24h() != null ? tokenPrice.getChange24h().toPlainString() : "");
        detailInfo.setMarketCap(tokenPrice.getMarketCap() != null ? tokenPrice.getMarketCap().toPlainString() : "");
        detailInfo.setFdvUsd(tokenPrice.getFdvUsd() != null ? tokenPrice.getFdvUsd().toPlainString() : "");
        return detailInfo;
    }

    @Override
    public List<MarketTokenDetailInfo> category(MarketTokenCategoryReq req) {
        int pageNum = req.getPageNum() == null ? DEFAULT_PAGE_NUM : req.getPageNum();
        int pageSize = req.getPageSize() == null ? DEFAULT_PAGE_SIZE : req.getPageSize();
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
        Map<String, MarketTokenInfo> baseMap = baseInfoList.stream().collect(Collectors.toMap(MarketTokenInfo::getCoinId, e -> e));
        List<MarketTokenPrice> priceList = marketTokenPriceMapper.queryByCoinIds(baseInfoList.stream().map(MarketTokenInfo::getCoinId).toList());
        Map<String, MarketTokenPrice> priceMap = priceList.stream().collect(Collectors.toMap(MarketTokenPrice::getCoinId, e -> e));

        List<MarketTokenDetailInfo> resultList = new ArrayList<>();
        categoryList.forEach(e -> {
            MarketTokenInfo baseInfo = baseMap.get(e.getCoinId());
            MarketTokenPrice price = priceMap.get(e.getCoinId());
            MarketTokenDetailInfo detailInfo = new MarketTokenDetailInfo();
            detailInfo.setCoinId(baseInfo.getCoinId());
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
