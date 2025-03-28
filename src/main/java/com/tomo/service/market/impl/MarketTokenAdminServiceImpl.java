package com.tomo.service.market.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.dto.MarketTokenDTO;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.req.MarketTokenQueryReq;
import com.tomo.service.market.MarketTokenAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 市场代币管理服务实现
 */
@Slf4j
@Service
public class MarketTokenAdminServiceImpl implements MarketTokenAdminService {

    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;

    @Override
    public void updateMarketToken(MarketTokenDTO tokenDTO) {
        MarketTokenInfo marketTokenInfo = marketTokenInfoMapper.selectByChainIndexAndAddress(tokenDTO.getChainIndex(), tokenDTO.getAddress());
        if (marketTokenInfo != null) {
            BeanUtils.copyProperties(tokenDTO, marketTokenInfo);
            marketTokenInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            marketTokenInfoMapper.updateById(marketTokenInfo);
        }
    }

    @Override
    public IPage<MarketTokenDTO> pageMarketToken(MarketTokenQueryReq queryDTO) {
        Page pg = new Page(queryDTO.getPage(), queryDTO.getSize());
        IPage<MarketTokenDTO> tokens = marketTokenInfoMapper.pageMarketToken(queryDTO, pg);

        Page<MarketTokenDTO> response = new Page<>();
        response.setRecords(tokens.getRecords());
        response.setTotal(tokens.getTotal());
        response.setSize(tokens.getSize());
        return response;
    }
} 