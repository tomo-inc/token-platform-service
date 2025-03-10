package com.tomo.controller;

import com.tomo.model.ChainUtil;
import com.tomo.model.ResultUtils;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenRankDTO;
import com.tomo.model.req.OnchainTokenReq;
import com.tomo.model.req.PlatformTokenReq;
import com.tomo.model.resp.OnChainTokenInfo;
import com.tomo.model.resp.OnChainTokenPrice;
import com.tomo.model.resp.Result;
import com.tomo.service.category.CoinGeckoService;
import com.tomo.service.category.TokenInfoService;
import com.tomo.service.category.TokenRankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/token-service/token-category")
public class TokenCategoryController {
    @Autowired
    private TokenInfoService tokenInfoService;
    @Autowired
    private CoinGeckoService coinGeckoService;
    @Autowired
    private TokenRankService tokenRankService;


    // name 可以模糊搜索，address需要全部。name，address 二选一
    @GetMapping("/query/token-info-price/platform/fuzz")
    public Result<List<TokenInfoDTO>> fuzzQueryPlatformToken(@RequestParam(value = "chainId", required = false) Long chainId,
                                                             @RequestParam("addressOrName") String addressOrName) {
        return ResultUtils.success(tokenInfoService.fuzzQueryPlatformToken(chainId, addressOrName));
    }

    // 精确查询平台币
    @GetMapping("/query/token-info-price/platform/exact")
    public Result<TokenInfoDTO> exactQueryPlatformToken(@RequestParam(value = "chainId") Long chainId,
                                                        @RequestParam("address") String address) {
        TokenInfoDTO tokenInfoDTO = tokenInfoService.exactQueryToken(chainId, address);
        if (tokenInfoDTO != null) {
            if (TokenInfoService.needUpdate(tokenInfoDTO.getUpdateTime().getTime())) {
                PlatformTokenReq tokenCategoryCoinGeckoDTO = new PlatformTokenReq();
                tokenCategoryCoinGeckoDTO.setCoingeckoCoinId(tokenInfoDTO.getCoingeckoCoinId());
                Map<String, TokenInfoDTO> tokenInfoDTOMap = coinGeckoService.singlePlatformTokenInfoAndPrice(tokenCategoryCoinGeckoDTO,false);
                return ResultUtils.success(tokenInfoDTOMap.get(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress())));
            }
            return ResultUtils.success(tokenInfoDTO);
        }
        return ResultUtils.success(null);
    }


    @PostMapping("/query/onchain/token-infos")
    public Result<List<OnChainTokenInfo>> queryOnChainTokenInfos(@RequestBody List<OnchainTokenReq> onchainTokenReqs) {
        List<OnChainTokenInfo> tokenInfos = coinGeckoService.queryOnchainTokenInfos(onchainTokenReqs);
        return ResultUtils.success(tokenInfos);
    }

    @PostMapping("/query/onchain/token-prices")
    public Result<List<OnChainTokenPrice>> queryOnChainTokenPrices(@RequestBody List<OnchainTokenReq> onchainTokenReqs) {
        List<OnChainTokenPrice> tokenPrices = coinGeckoService.queryOnChainTokenPrices(onchainTokenReqs);
        return ResultUtils.success(tokenPrices);
    }

    /**
     * 查询币价和代币信息
     *
     * @param onchainTokenReqs
     * @return
     */
    @PostMapping("/query/token-info-price/onchain/exact")
    public Result<Map<String, TokenInfoDTO>> exactQueryOnchainToken(@RequestBody List<OnchainTokenReq> onchainTokenReqs) {
        long l = System.currentTimeMillis();
        Map<String, TokenInfoDTO> tokenInfoDTOMap = coinGeckoService.batchOnchainCoinInfoAndPrice(onchainTokenReqs, false);
        System.out.println(System.currentTimeMillis() - l);
        return ResultUtils.success(tokenInfoDTOMap);
    }

    /**
     * 查询币价和代币信息
     *
     * @param onchainTokenReqs
     * @return
     */
    @PostMapping("/query/token-info-price/onchain/exact2")
    public Result<Map<String, TokenInfoDTO>> exactQueryOnchainToken2(@RequestBody List<OnchainTokenReq> onchainTokenReqs) {
        long l = System.currentTimeMillis();
        Map<String, TokenInfoDTO> tokenInfoDTOMap = coinGeckoService.batchOnchainCoinInfoAndPriceV2(onchainTokenReqs, false);
        System.out.println(System.currentTimeMillis() - l);
        return ResultUtils.success(tokenInfoDTOMap);
    }

    @PostMapping("/query/token-info-price/native/token")
    public Result<Map<String, TokenInfoDTO>> queryNativeToken(@RequestBody List<PlatformTokenReq> tokens) {
        Map<String, TokenInfoDTO> stringTokenInfoDTOMap = coinGeckoService.updateNativeOrPlatformPrice(tokens);
        return ResultUtils.success(stringTokenInfoDTOMap);
    }


    @GetMapping("/query/token-ranking")
    public Result<List<TokenRankDTO>> tokenRiskData(@RequestParam("tag") String tag,
                                                    @RequestParam("chainId") String chainId) {
        return ResultUtils.success(tokenRankService.getOKXTrending(chainId));
    }
}
