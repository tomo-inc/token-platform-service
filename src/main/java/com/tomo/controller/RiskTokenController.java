package com.tomo.controller;

import com.tomo.model.ResultUtils;
import com.tomo.model.dto.TokenRiskGoplusDTO;
import com.tomo.model.req.RiskTokenDataReq;
import com.tomo.model.resp.Result;
import com.tomo.service.risk.BlackWhiteListService;
import com.tomo.service.risk.RiskTokenDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/wallet/risk-token")
public class RiskTokenController {
    @Autowired
    BlackWhiteListService blackWhiteListService;
    @Autowired
    RiskTokenDataService<TokenRiskGoplusDTO> riskTokenDataService;

    @GetMapping("/query/black-white-list")
    public Result<Boolean> blackWhiteList(Long chainId, String address) {
        return ResultUtils.success(blackWhiteListService.isBlackList(chainId, address));
    }

    @PostMapping("/query/token-risk-data")
    public Result<TokenRiskGoplusDTO> tokenRiskData(@RequestBody RiskTokenDataReq riskTokenList) {
        return ResultUtils.success(riskTokenDataService.getRiskToken(riskTokenList));
    }
}
