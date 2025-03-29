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

        return List.of();
    }

    @Override
    public List<MarketTokenBaseInfo> category(MarketTokenCategoryReq req) {
        return List.of();
    }

    @Override
    public List<MarketTokenHistory> history(Long chainIndex, String address) {
        return List.of();
    }
}
