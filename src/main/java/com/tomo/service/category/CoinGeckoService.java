package com.tomo.service.category;


import com.tomo.model.IntervalEnum;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.req.TokenReq;

import java.util.List;
import java.util.Map;

public interface CoinGeckoService {
    // 更新多个onchain币价同时更新代币信息
    // key: chainId+ "-" +address
    Map<String, TokenInfoDTO> batchOnchainCoinInfoAndPrice(List<TokenReq> tokenList, boolean include);

    Map<String, TokenInfoDTO> singleOnchainTokenInfoAndPrice(List<TokenReq> partition,boolean include);

    // 更新多个platform币价同时更新代币信息
    // key: chainId+ "-" +address
    Map<String, TokenInfoDTO> batchPlatformCoinInfoAndPrice(List<TokenCategoryCoinGeckoDTO> tokenList);

    // 更新单个platform币价同时更新代币信息
    // key: chainId+ "-" +address
    Map<String, TokenInfoDTO> singlePlatformTokenInfoAndPrice(TokenCategoryCoinGeckoDTO token);

    // 查询k线
    List<TokenOhlcvDTO> getPlatformTokenOhlcv(Long chainId, String address, IntervalEnum interval);

}
