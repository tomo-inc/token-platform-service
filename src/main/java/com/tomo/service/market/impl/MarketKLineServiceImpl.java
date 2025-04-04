package com.tomo.service.market.impl;

import com.tomo.feign.CoinGeckoClient;
import com.tomo.feign.OKXClient;
import com.tomo.mapper.MarketTokenInfoMapper;
import com.tomo.mapper.MarketTokenPriceMapper;
import com.tomo.model.ChainEnum;
import com.tomo.model.ChainUtil;
import com.tomo.model.market.enums.IntervalEnum;
import com.tomo.model.market.MarketTokenInfo;
import com.tomo.model.market.MarketTokenPrice;
import com.tomo.model.resp.MarketOHLCVInfo;
import com.tomo.model.resp.OKXResult;
import com.tomo.model.resp.OnchainOHLCVResp;
import com.tomo.service.market.MarketKLineService;
import com.tomo.service.market.MarketTokenDaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class MarketKLineServiceImpl implements MarketKLineService {

    @Autowired
    private MarketTokenDaoService marketTokenDaoService;

    @Autowired
    private MarketTokenPriceMapper marketTokenPriceMapper;

    @Autowired
    private MarketTokenInfoMapper marketTokenInfoMapper;

    @Autowired
    private OKXClient okxClient;

    @Autowired
    private CoinGeckoClient coinGeckoClient;


    @Override
    public List<MarketOHLCVInfo> getKLine(Long chainIndex, String address, String interval, Long beforeTs,
                                          Integer limit) {


        // TODO: dex scan 不支持的链
        String coinId = ChainUtil.getTokenKey(chainIndex, address);
        MarketTokenInfo tokenInfo = marketTokenInfoMapper.getByCoinId(coinId);
        MarketTokenPrice tokenPrice = marketTokenPriceMapper.getByCoinId(coinId);
        if (tokenInfo == null || tokenPrice == null) {
            return new ArrayList<>();
        }
        IntervalEnum intervalEnum = IntervalEnum.fromValue(interval);
        if (intervalEnum == null) {
            return new ArrayList<>();
        }

        if ((tokenInfo.getIsNative() != null && tokenInfo.getIsNative()) || !StringUtils.hasText(tokenPrice.getPoolAddress())) {
            String it = switch (intervalEnum) {
                case DAY -> "1Dutc";
                case WEEK -> "1Wutc";
                case MONTH -> "1Mutc";
                case HOUR -> "1H";
                case FOUR_HOUR -> "4H";
                case MINUTE -> "1m";
                case FIVE_MINUTE -> "5m";
                case FIFTEEN_MINUTE -> "15m";
            };
            String instId = tokenInfo.getSymbol().toUpperCase() + "-USDT";
            OKXResult<List<List<Double>>> listOKXResult = okxClient.marketCandles(instId, it);
            List<List<Double>> res = listOKXResult.getData();
            Collections.reverse(res);
            return res.stream().map(t ->
                    MarketOHLCVInfo.builder().timestamp(t.get(0).longValue())
                            .open(t.get(1))
                            .high(t.get(2))
                            .low(t.get(3))
                            .close(t.get(4))
                            .volume(t.get(5)).build()
            ).toList();
        } else {
            ChainEnum chainEnum = ChainEnum.getChanByIndex(chainIndex);
            String networkId = ChainUtil.getChainAndCoinGeckoMap().get(chainEnum.getChainId()).getCoinGeckoEnum().getChainIdentityId();
            String poolAddress = tokenPrice.getPoolAddress();
            if (networkId == null) {
                return new ArrayList<>();
            }
            if (poolAddress == null) {
                return new ArrayList<>();
            }
            String it = switch (intervalEnum) {
                case DAY, WEEK, MONTH -> "day";
                case HOUR, FOUR_HOUR -> "hour";
                case MINUTE, FIVE_MINUTE, FIFTEEN_MINUTE -> "minute";
            };
            Integer aggregate = switch (intervalEnum) {
                case DAY, MINUTE, HOUR, WEEK, MONTH -> 1;
                case FOUR_HOUR -> 4;
                case FIVE_MINUTE -> 5;
                case FIFTEEN_MINUTE -> 15;
            };
            OnchainOHLCVResp res;
            try {
                res = coinGeckoClient.getOnchainOHLCV(networkId, poolAddress, it, limit, aggregate, tokenPrice.getIsPoolBaseToken() ? "base" : "quote");
            } catch (HttpClientErrorException.NotFound e) {
                return new ArrayList<>();
            }
            List<MarketOHLCVInfo> result = new ArrayList<>(res.getData().getAttributes().getOhlcvList().stream().map(t ->
                    MarketOHLCVInfo.builder().
                            timestamp(t.get(0).longValue() * 1000L)
                            .open(t.get(1))
                            .high(t.get(2))
                            .low(t.get(3))
                            .close(t.get(4))
                            .volume(t.get(5)).build()
            ).toList());

            Collections.reverse(result);
            if (intervalEnum == IntervalEnum.WEEK) {
                result = mergeOhlcv(result, 7);
            }
            if (intervalEnum == IntervalEnum.MONTH) {
                result = mergeOhlcv(result, 30);
            }
            return result;
        }

    }

    private List<MarketOHLCVInfo> mergeOhlcv(List<MarketOHLCVInfo> list, Integer count) {
        List<MarketOHLCVInfo> merged = new ArrayList<>();
        for (int i = 0; i < list.size(); i += count) {
            List<MarketOHLCVInfo> toMerge = list.subList(i, Math.min(i + count, list.size()));

            long timestamp = toMerge.get(0).getTimestamp();
            double open = toMerge.get(0).getOpen();
            double high = toMerge.stream().max(Comparator.comparingDouble(MarketOHLCVInfo::getHigh)).get().getHigh();
            double low = toMerge.stream().min(Comparator.comparingDouble(MarketOHLCVInfo::getLow)).get().getLow();
            double close = toMerge.get(toMerge.size() - 1).getClose();
            double volume = toMerge.stream().mapToDouble(MarketOHLCVInfo::getVolume).sum();

            merged.add(
                    MarketOHLCVInfo.builder().
                            timestamp(timestamp)
                            .open(open)
                            .high(high)
                            .low(low)
                            .close(close)
                            .volume(volume).build()
            );
        }
        return merged;
    }
}
