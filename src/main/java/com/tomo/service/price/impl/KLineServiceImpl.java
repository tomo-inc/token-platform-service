package com.tomo.service.price.impl;

import com.tomo.feign.CoinGeckoClient;
import com.tomo.feign.OKXClient;
import com.tomo.model.ChainUtil;
import com.tomo.model.market.enums.IntervalEnum;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.req.OnchainTokenReq;
import com.tomo.model.resp.OKXResult;
import com.tomo.model.resp.OnchainOHLCVResp;
import com.tomo.service.category.CoinGeckoService;
import com.tomo.service.category.TokenInfoService;
import com.tomo.service.price.BitqueryService;
import com.tomo.service.price.KLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class KLineServiceImpl implements KLineService {

    @Autowired
    OKXClient okxClient;
    @Autowired
    CoinGeckoClient coinGeckoClient;
    @Autowired
    CoinGeckoService geckoService;
    @Autowired
    TokenInfoService tokenInfoService;

    @Autowired
    BitqueryService bitqueryService;
    @Autowired
    private CoinGeckoService coinGeckoService;


    // 改成coingecko
//    @Override
//    public List<SolKlineDTO> getSolTokenKline(String address, IntervalEnum interval) {
//        TokenCategoryCoinGeckoDTO tokenCategory = tokenCategoryService.getTokenCategory(ChainInfoEnum.SOLANA.getChainId(), address, null);
//        if (tokenCategory == null) {
//            return null;
//        }
//        TokenPriceDTO tokenPriceDTO = tokenPriceDataService.listOne(tokenCategory.getChainId(),tokenCategory.getAddress());
//
//        String itUnit;
//        int it;
//        switch (interval) {
//            case DAY -> {
//                itUnit = "days";
//                it = 1;
//            }
//            case WEEK -> {
//                itUnit = "weeks";
//                it = 1;
//            }
//            case MONTH -> {
//                itUnit = "months";
//                it = 1;
//            }
//            case HOUR -> {
//                itUnit = "hours";
//                it = 1;
//            }
//            case FOUR_HOUR -> {
//                itUnit = "hours";
//                it = 4;
//            }
//            case MINUTE -> {
//                itUnit = "minutes";
//                it = 1;
//            }
//            case FIVE_MINUTE -> {
//                itUnit = "minutes";
//                it = 5;
//            }
//            case FIFTEEN_MINUTE -> {
//                itUnit = "minutes";
//                it = 15;
//            }
//            default -> throw new RuntimeException("Unsupported interval");
//        };
//        return bitqueryService.getSolanaKline(tokenCategory.getAddress(), tokenPriceDTO.getPoolAddress(), it, itUnit, 300);
//    }


    // 只提供一个接口
    @Override
    public List<TokenOhlcvDTO> getTokenOhlcv(Long chainId, String address, IntervalEnum interval) {
        TokenInfoDTO tokenInfoDTO = coinGeckoService.queryOneByOnchain(new OnchainTokenReq(chainId, address), true);
        if (tokenInfoDTO == null) {
            return new ArrayList<>();
        }
        if ((tokenInfoDTO.getIsNative() != null && tokenInfoDTO.getIsNative()) || !StringUtils.hasText(tokenInfoDTO.getPoolAddress())) {
            String it = switch (interval) {
                case DAY -> "1Dutc";
                case WEEK -> "1Wutc";
                case MONTH -> "1Mutc";
                case HOUR -> "1H";
                case FOUR_HOUR -> "4H";
                case MINUTE -> "1m";
                case FIVE_MINUTE -> "5m";
                case FIFTEEN_MINUTE -> "15m";
            };
            String coinId = tokenInfoDTO.getSymbol().toUpperCase() + "-USDT";
            OKXResult<List<List<Double>>> listOKXResult = okxClient.marketCandles(coinId, it);
            List<List<Double>> res = listOKXResult.getData();
            Collections.reverse(res);
            return res.stream().map((t) ->
                    new TokenOhlcvDTO()
                            .setTimestamp(t.get(0).longValue())
                            .setOpen(t.get(1))
                            .setHigh(t.get(2))
                            .setLow(t.get(3))
                            .setClose(t.get(4))
                            .setVolume(t.get(7))
            ).toList();
        } else {
            String networkId = ChainUtil.getChainAndCoinGeckoMap().get(tokenInfoDTO.getChainId()).getCoinGeckoEnum().getChainIdentityId();
            String poolAddress = tokenInfoDTO.getPoolAddress();
            if (networkId == null) {
                return new ArrayList<>();
            }
            if (poolAddress == null) {
                return new ArrayList<>();
            }
            String it = switch (interval) {
                case DAY, WEEK, MONTH -> "day";
                case HOUR, FOUR_HOUR -> "hour";
                case MINUTE, FIVE_MINUTE, FIFTEEN_MINUTE  -> "minute";
            };
            Integer limit = switch (interval) {
                case DAY -> 180;
                case HOUR -> 14 * 24;
                case FOUR_HOUR -> 14 * 6;
                case MINUTE, FIVE_MINUTE, FIFTEEN_MINUTE, WEEK, MONTH -> 1000;
            };
            Integer aggregate = switch (interval) {
                case DAY, MINUTE, HOUR, WEEK, MONTH -> 1;
                case FOUR_HOUR -> 4;
                case FIVE_MINUTE-> 5;
                case FIFTEEN_MINUTE -> 15;
            };
            OnchainOHLCVResp res;
            try {
                res = coinGeckoClient.getOnchainOHLCV(networkId, poolAddress, it, limit, aggregate, tokenInfoDTO.getIsPoolBaseToken() ? "base" : "quote");
            } catch (HttpClientErrorException.NotFound e) {
                return new ArrayList<>();
            }
            List<TokenOhlcvDTO> result = new ArrayList<>(res.getData().getAttributes().getOhlcvList().stream().map((t) ->
                    new TokenOhlcvDTO()
                            .setTimestamp(t.get(0).longValue() * 1000L)
                            .setOpen(t.get(1))
                            .setHigh(t.get(2))
                            .setLow(t.get(3))
                            .setClose(t.get(4))
                            .setVolume(t.get(5))
            ).toList());
            Collections.reverse(result);
            if (interval == IntervalEnum.WEEK) {
                result = mergeOhlcv(result, 7);
            }
            if (interval == IntervalEnum.MONTH) {
                result = mergeOhlcv(result, 30);
            }
            return result;
        }
    }

    private List<TokenOhlcvDTO> mergeOhlcv(List<TokenOhlcvDTO> list, Integer count) {
        List<TokenOhlcvDTO> merged = new ArrayList<>();
        for (int i = 0; i < list.size(); i += count) {
            List<TokenOhlcvDTO> toMerge = list.subList(i, Math.min(i + count, list.size()));

            long timestamp = toMerge.get(0).getTimestamp();
            double open = toMerge.get(0).getOpen();
            double high = toMerge.stream().max(Comparator.comparingDouble(TokenOhlcvDTO::getHigh)).get().getHigh();
            double low = toMerge.stream().min(Comparator.comparingDouble(TokenOhlcvDTO::getLow)).get().getLow();
            double close = toMerge.get(toMerge.size() - 1).getClose();
            double volume = toMerge.stream().mapToDouble(TokenOhlcvDTO::getVolume).sum();

            merged.add(
                    new TokenOhlcvDTO()
                            .setTimestamp(timestamp)
                            .setOpen(open)
                            .setHigh(high)
                            .setLow(low)
                            .setClose(close)
                            .setVolume(volume)
            );
        }
        return merged;
    }



}
