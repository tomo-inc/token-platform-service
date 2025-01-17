package com.tomo.feign;

import com.tomo.model.resp.CoinInfoResp;
import com.tomo.model.resp.CoinMarketDataResp;
import com.tomo.model.resp.CoinSimpleInfoResp;
import com.tomo.model.resp.DexTokenResp;
import com.tomo.model.resp.DexTokensPriceResp;
import com.tomo.model.resp.NativeCoinSimpleInfoResp;
import com.tomo.model.resp.OnchainOHLCVResp;
import com.tomo.model.resp.TokenPriceResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

// todo network
@FeignClient(name = "coingecko-feign",
             url = "https://pro-api.coingecko.com/api/v3",
             configuration = CoinGeckoClientConfig.class)
public interface CoinGeckoClient {
    // platform
    @GetMapping("/asset_platforms")
    List<NativeCoinSimpleInfoResp> getAllNativeCoinList();

    @GetMapping("/coins/list?include_platform=false")
    List<CoinSimpleInfoResp> getAllCoinList();

    @GetMapping("/simple/price?ids={coinId}&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=false")
    Map<String, TokenPriceResp> getPlatformTokenPrice(@PathVariable("coinId") String coinId);

    @GetMapping("/coins/{coinId}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
    CoinInfoResp getPlatformCoinInfo(@PathVariable("coinId") String coinId);

    @GetMapping("/coins/markets?vs_currency=usd&ids={coinId}")
    List<CoinMarketDataResp> getTokenMarketInfo(@PathVariable("coinId") String coinId);

    @GetMapping("/coins/{coinId}/ohlc?vs_currency=usd&days={interval}&interval={days}")
    List<List<Double>> getPlatformTokenOHLCV(@PathVariable("coinId") String coinId,
                                             @PathVariable("interval") String interval,
                                             @PathVariable("days") Integer days);



    // onchain
    @GetMapping("/onchain/networks/{network}/pools/{poolAddress}/ohlcv/{interval}?limit={limit}&aggregate={aggregate}&token={token}")
    OnchainOHLCVResp getOnchainOHLCV(@PathVariable("network") String network,
                                     @PathVariable("poolAddress") String poolAddress,
                                     @PathVariable("interval")  String interval,
                                     @PathVariable("limit") Integer limit,
                                     @PathVariable("aggregate") Integer aggregate,
                                     @PathVariable("token") String token);


    @GetMapping("/onchain/networks/{network}/tokens/multi/{address}?include=top_pools")
    DexTokenResp batchGetTokenInfo(@PathVariable("network") String network,
                                   @PathVariable("address") String address) ;

    @GetMapping("/onchain/networks/{network}/tokens/multi/{address}")
    DexTokenResp batchGetSimpleTokenInfo(@PathVariable("network") String network,
                                         @PathVariable("address") String address) ;


    @GetMapping("/onchain/simple/networks/{network}/token_price/{address}")
    DexTokensPriceResp batchGetTokenPrice(@PathVariable("network") String network,
                                          @PathVariable("address") List<String> addresses);



}
