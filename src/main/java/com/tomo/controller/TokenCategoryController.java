package com.tomo.controller;

import com.tomo.model.ResultUtils;
import com.tomo.model.dto.TokenCategoryCoinGeckoDTO;
import com.tomo.model.dto.TokenInfoDTO;
import com.tomo.model.dto.TokenRankDTO;
import com.tomo.model.req.TokenReq;
import com.tomo.model.resp.Result;
import com.tomo.service.category.CoinGeckoService;
import com.tomo.service.category.TokenInfoService;
import com.tomo.service.category.TokenRankService;
import com.tomo.model.ChainUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                TokenCategoryCoinGeckoDTO tokenCategoryCoinGeckoDTO = new TokenCategoryCoinGeckoDTO();
                tokenCategoryCoinGeckoDTO.setCoingeckoChainId(tokenInfoDTO.getCoingeckoCoinId());
                Map<String, TokenInfoDTO> tokenInfoDTOMap = coinGeckoService.singlePlatformTokenInfoAndPrice(tokenCategoryCoinGeckoDTO);
                return ResultUtils.success(tokenInfoDTOMap.get(ChainUtil.getCommonKey(tokenInfoDTO.getChainId(), tokenInfoDTO.getAddress())));
            }
            return ResultUtils.success(tokenInfoDTO);
        }
        return ResultUtils.success(null);
    }


    @PostMapping("/query/token-info-price/onchain/exact")
    public Result<Map<String, TokenInfoDTO>> exactQueryOnchainToken(@RequestBody List<TokenReq> tokenReqs) {
        Map<String, TokenInfoDTO> tokenInfoDTOMap = coinGeckoService.batchOnchainCoinInfoAndPrice(tokenReqs, false);
        return ResultUtils.success(tokenInfoDTOMap);
    }


    @GetMapping("/query/token-ranking")
    public Result<List<TokenRankDTO>> tokenRiskData(@RequestParam("tag") String tag,
                                                    @RequestParam("chainId") String chainId) {
        return ResultUtils.success(tokenRankService.getOKXTrending(chainId));
    }
}
