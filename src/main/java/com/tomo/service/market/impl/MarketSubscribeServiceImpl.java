package com.tomo.service.market.impl;

import com.tomo.mapper.MarketSubscribeInfoMapper;
import com.tomo.mapper.MarketTokenCategoryMapper;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.model.ChainUtil;
import com.tomo.model.MarketSubscribeInfo;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.req.MarketSubscribeReq;
import com.tomo.model.resp.MarketSubscribeResp;
import com.tomo.service.market.MarketSubscribeService;
import com.tomo.service.market.MarketTokenDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.swing.text.rtf.RTFEditorKit;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketSubscribeServiceImpl implements MarketSubscribeService {

    @Autowired
    private MarketSubscribeInfoMapper marketSubscribeInfoMapper;

    @Autowired
    private MarketTokenDaoService marketTokenDaoService;
    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;

    @Override
    public void batchSubscribe(List<MarketSubscribeReq> req) {
        if (CollectionUtils.isEmpty(req)) {
            return;
        }
        List<MarketSubscribeInfo> list = req.stream().map(
                item -> {
                    MarketSubscribeInfo info = new MarketSubscribeInfo();
                    info.setChainIndex(item.getChainIndex());
                    info.setAddress(item.getAddress() == null ? "" : item.getAddress());
                    info.setType(item.getType());
                    info.setStatus(1);
                    info.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                    info.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
                    return info;
                }
        ).toList();
        marketSubscribeInfoMapper.batchInsert(list);

    }

    @Override
    public void unSubscribe(List<MarketSubscribeReq> req) {
        if (CollectionUtils.isEmpty(req)) {
            return;
        }
        List<MarketSubscribeInfo> list = req.stream().map(
                item -> {
                    MarketSubscribeInfo info = new MarketSubscribeInfo();
                    info.setChainIndex(item.getChainIndex());
                    info.setAddress(item.getAddress() == null ? "" : item.getAddress());
                    info.setType(item.getType());
                    info.setStatus(2);
                    info.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                    info.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
                    return info;
                }
        ).toList();
        marketSubscribeInfoMapper.batchUpdate(list);
    }

    @Override
    public List<MarketSubscribeResp> list(Long chainIndex, String address, Integer pageNum, Integer pageSize) {
        List<MarketSubscribeInfo> infoList = marketSubscribeInfoMapper.pageList(chainIndex, address, (pageNum - 1) * pageSize, pageSize);
        if (CollectionUtils.isEmpty(infoList)) {
            return new ArrayList<>();
        }

        List<String> coinIdList = infoList.stream().map(e -> ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress())).toList();
        List<MarketTokenInfo> tokenInfos = marketTokenInfoMapper.queryByCoinIds(coinIdList);
        Map<String, MarketTokenInfo> tokenInfoMap = tokenInfos.stream().collect(Collectors.toMap(MarketTokenInfo::getCoinId, e -> e));
        return infoList.stream().map(e -> {
            MarketTokenInfo tokenInfo = tokenInfoMap.get(ChainUtil.getTokenKey(e.getChainIndex(), e.getAddress()));
            MarketSubscribeResp resp = new MarketSubscribeResp();
            resp.setChainIndex(e.getChainIndex());
            resp.setAddress(e.getAddress());
            resp.setType(e.getType());
            resp.setStatus(e.getStatus());
            if (tokenInfo != null) {
                resp.setSymbol(tokenInfo.getSymbol());
            }
            return resp;
        }).toList();

    }
}
