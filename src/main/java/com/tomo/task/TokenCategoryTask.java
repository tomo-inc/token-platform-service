package com.tomo.task;


import com.tomo.feign.CoinGeckoClient;
import com.tomo.model.ChainInfoEnum;
import com.tomo.model.ChainUtil;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.model.resp.CoinSimpleInfoResp;
import com.tomo.model.resp.NativeCoinSimpleInfoResp;
import com.tomo.service.category.TokenCategoryDataService;
import com.tomo.service.category.impl.CoinGeckoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<TokenCategoryCoinGeckoDTO> list = new ArrayList<>();
        Map<String, ChainInfoEnum> coinGeckoChainInfoMap = ChainUtil.getCoinGeckoChainInfoMap();
        for (NativeCoinSimpleInfoResp nativeCoinSimpleInfoResp : allNativeCoinList) {
            if (!coinGeckoChainInfoMap.containsKey(nativeCoinSimpleInfoResp.getId())){
                continue;
            }
            ChainInfoEnum chainInfoEnum = coinGeckoChainInfoMap.get(nativeCoinSimpleInfoResp.getId());
            TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO = new TokenCategoryCoinGeckoDTO();
            tokenCategoryCoinGeckoDTO.setCoingeckoCoinId(nativeCoinSimpleInfoResp.getNativeCoinId());
            tokenCategoryCoinGeckoDTO.setName(nativeCoinSimpleInfoResp.getName());
            tokenCategoryCoinGeckoDTO.setIsNative(true);
            tokenCategoryCoinGeckoDTO.setChainId(chainInfoEnum.getChainId());
            boolean exists = tokenCategoryDataService.exists(nativeCoinSimpleInfoResp.getId());
            if (!exists) {
                list.add(tokenCategoryCoinGeckoDTO);
            }
        }
        coinGeckoService.batchPlatformCoinInfoAndPrice(list);
    }

    public void allTokenBriefInfoTask() {
        List<CoinSimpleInfoResp> coinSimpleInfoResps = coinGeckoClient.getAllCoinList();
        List<TokenCategoryCoinGeckoDTO> list = new ArrayList<>();
        for (CoinSimpleInfoResp coinSimpleInfoResp : coinSimpleInfoResps) {
            TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO = new TokenCategoryCoinGeckoDTO();
            tokenCategoryCoinGeckoDTO.setCoingeckoCoinId(coinSimpleInfoResp.getId());
            tokenCategoryCoinGeckoDTO.setName(coinSimpleInfoResp.getName());
            tokenCategoryCoinGeckoDTO.setIsNative(false);
            boolean exists = tokenCategoryDataService.exists(coinSimpleInfoResp.getId());
            if (!exists) {
                list.add(tokenCategoryCoinGeckoDTO);
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
