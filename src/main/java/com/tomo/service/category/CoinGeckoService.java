package com.tomo.service.category;


import com.tomo.model.IntervalEnum;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.req.OnchainTokenReq;
import com.tomo.model.req.PlatformTokenReq;
import com.tomo.model.resp.OnChainTokenInfo;
import com.tomo.model.resp.OnChainTokenPrice;
import com.tomo.model.resp.CoinPriceResp;

import java.util.List;
import java.util.Map;

public interface CoinGeckoService {
    TokenInfoDTO queryOneByOnchain(OnchainTokenReq tokenReq, boolean include);

    List<OnChainTokenInfo> queryOnchainTokenInfos(List<OnchainTokenReq> tokenList);

    List<OnChainTokenPrice> queryOnChainTokenPrices(List<OnchainTokenReq> tokenList);


    // 更新多个onchain币价同时更新代币信息
    // key: chainId+ "-" +address
    Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPrice(List<OnchainTokenReq> tokenList, boolean include);

    Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPriceV2(List<OnchainTokenReq> rawTokenList, boolean include);

    Map<String, TokenInfoDTO> singleOnchainTokenInfoAndPrice(List<OnchainTokenReq> partition, boolean include, boolean saveToDb);

    // 更新多个platform币价同时更新代币信息
    // key: chainId+ "-" +address
    Map<String, TokenInfoDTO> batchPlatformCoinInfoAndPrice(List<PlatformTokenReq> tokenList,boolean onlyOne);

    // 更新单个platform币价同时更新代币信息
    // key: chainId+ "-" +address
    Map<String, TokenInfoDTO> singlePlatformTokenInfoAndPrice(PlatformTokenReq token, boolean onlyOne);

    Map<String, TokenInfoDTO> updateNativeOrPlatformPrice(List<PlatformTokenReq> tokens);

    // 查询k线
    List<TokenOhlcvDTO> getPlatformTokenOhlcv(Long chainId, String address, IntervalEnum interval);

    List<CoinPriceResp> batchOnchainCoinPrice(List<OnchainTokenReq> onchainTokenReqs, boolean b);
}
