package com.tomo.task;


import com.tomo.feign.CoinGeckoClient;
import com.tomo.model.ChainEnum;
import com.tomo.model.ChainUtil;
import com.tomo.model.CoinGeckoEnum;
import com.tomo.model.req.PlatformTokenReq;
import com.tomo.model.resp.CoinSimpleInfoResp;
import com.tomo.model.resp.NativeCoinSimpleInfoResp;
import com.tomo.service.category.TokenCategoryDataService;
import com.tomo.service.category.impl.CoinGeckoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TokenCategoryTask {

    @Autowired
    TokenCategoryDataService tokenCategoryDataService;
    @Autowired
    CoinGeckoServiceImpl coinGeckoService;
    @Autowired
    CoinGeckoClient coinGeckoClient;

    public void allNativeToken(){
        List<NativeCoinSimpleInfoResp> allNativeCoinList = coinGeckoClient.getAllNativeCoinList();
        List<PlatformTokenReq> list = new ArrayList<>();
        Map<String, Pair<CoinGeckoEnum, ChainEnum>> coinGeckoChainInfoMap = ChainUtil.getCoinGeckoChainInfoMap();
        for (NativeCoinSimpleInfoResp nativeCoinSimpleInfoResp : allNativeCoinList) {
            if (!coinGeckoChainInfoMap.containsKey(nativeCoinSimpleInfoResp.getId())){
                continue;
            }
            PlatformTokenReq tokenReq = new PlatformTokenReq();
            tokenReq.setCoingeckoCoinId(nativeCoinSimpleInfoResp.getNativeCoinId());
            boolean exists = tokenCategoryDataService.exists(nativeCoinSimpleInfoResp.getId());
            if (!exists) {
                list.add(tokenReq);
            }
        }
        coinGeckoService.batchPlatformCoinInfoAndPrice(list);
    }

    public void allTokenBriefInfoTask() {
        List<CoinSimpleInfoResp> coinSimpleInfoResps = coinGeckoClient.getAllCoinList();
        List<PlatformTokenReq> list = new ArrayList<>();
        for (CoinSimpleInfoResp coinSimpleInfoResp : coinSimpleInfoResps) {
            PlatformTokenReq tokenReq = new PlatformTokenReq();
            tokenReq.setCoingeckoCoinId(coinSimpleInfoResp.getId());
            boolean exists = tokenCategoryDataService.exists(coinSimpleInfoResp.getId());
            if (!exists) {
                list.add(tokenReq);
            }
        }
        coinGeckoService.batchPlatformCoinInfoAndPrice(list);
    }


//    // 向一个redis里面注册代币，每隔10s中循环取出这些代币，刷新价格
//    @Scheduled(fixedRate = 10000) // 每隔 10 秒执行一次
//    public void refreshTokenPrices() {
//        List<TokenReq> tokens = tokenService.getTokens();
//        if (tokens!= null && !tokens.isEmpty()) {
//            Map<String, TokenInfoDTO> stringTokenInfoDTOMap = coinGeckoService.batchOnchainCoinInfoAndPrice(tokens, false);
//
//        }
//    }


}
