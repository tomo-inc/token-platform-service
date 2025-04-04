package com.tomo.controller;

import com.tomo.feign.CoinGeckoClient;
import com.tomo.model.ResultUtils;
import com.tomo.model.resp.Result;
import com.tomo.model.resp.coingecko.CoinGeckoTokenInfo;
import com.tomo.service.market.GDexClientService;
import com.tomo.service.market.MarketTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private CoinGeckoClient coinGeckoClient;

    @Autowired
    private MarketTokenService marketTokenService;

    @Autowired
    private GDexClientService gDexClientService;

    @GetMapping("/test/coinGeckoTokenInfo")
    public Result<CoinGeckoTokenInfo> testCoinGeckoTokenInfo(String network, String address) {
        CoinGeckoTokenInfo tokenInfo = coinGeckoClient.getTokenInfo(network, address);
        return ResultUtils.success(tokenInfo);
    }

    @GetMapping("/test/testUpdateSocial")
    public Result testUpdateSocial() {
        marketTokenService.updateSocialInfo();
        return ResultUtils.success("ok");
    }

    @GetMapping("/test/testGDexClient")
    public Result testGDexClient() {
//        gDexClientService.getClint(1L);
        return ResultUtils.success("ok");
    }


}
