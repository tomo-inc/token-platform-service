package com.tomo.service.market.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomo.mapper.MarketTokenCategoryMapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.market.MarketTokenCategory;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.req.MarketTokenQueryReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.service.market.MarketTokenAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 市场代币管理服务实现
 */
@Slf4j
@Service
public class MarketTokenAdminServiceImpl implements MarketTokenAdminService {

    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;
    @Autowired
    private MarketTokenCategoryMapper marketTokenCategoryMapper;

    @Override
    public void updateMarketToken(MarketTokenBaseInfo tokenDTO) {
        MarketTokenInfo marketTokenInfo = marketTokenInfoMapper.selectByChainIndexAndAddress(tokenDTO.getChainIndex(), tokenDTO.getAddress());
        if (marketTokenInfo != null) {
            BeanUtils.copyProperties(tokenDTO, marketTokenInfo);
            marketTokenInfo.setTotalSupply(new BigDecimal(tokenDTO.getTotalSupply()));
            marketTokenInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            marketTokenInfoMapper.updateById(marketTokenInfo);
        }
    }

    @Override
    public IPage<MarketTokenBaseInfo> pageMarketToken(MarketTokenQueryReq queryDTO) {
        Page pg = new Page(queryDTO.getPage(), queryDTO.getSize());
        IPage<MarketTokenInfo> tokens = marketTokenInfoMapper.pageMarketToken(queryDTO, pg);

        Page<MarketTokenBaseInfo> response = new Page<>();
        List<MarketTokenInfo> records = tokens.getRecords();

        List<Long> coinIds = records.stream().map(MarketTokenInfo::getId).toList();
        List<MarketTokenCategory> categoryList = marketTokenCategoryMapper.queryByCoinIds(coinIds);

        List<MarketTokenBaseInfo> resultList = new ArrayList<>();
        records.forEach(tokenInfo -> {
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


        response.setRecords(resultList);
        response.setTotal(tokens.getTotal());
        response.setSize(tokens.getSize());
        return response;
    }
} 