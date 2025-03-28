package com.tomo.controller;

import com.tomo.model.ResultUtils;
import com.tomo.model.dto.MemeTokenDTO;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.req.MemeTokenQueryReq;
import com.tomo.model.resp.Result;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/wallet/token")
public class TokenController {
    @Autowired
    TokenService tokenService;

    @GetMapping("/search")
    public Result<List<TokenDTO>> search(@RequestHeader("authorization") String authorization, @RequestParam String content, @RequestParam(required = false) String chain) {
        List<TokenDTO> tokenDTOS = tokenService.tokenSearch(authorization, content, chain);
        return ResultUtils.success(tokenDTOS);
    }

    @GetMapping("/meme/detail")
    public Result<TokenDTO> memeTokenDetail(@RequestHeader("authorization") String authorization, @RequestParam String tokenName) {
        TokenDTO tokenDTO = tokenService.tokenDetail(authorization, tokenName);
        return ResultUtils.success(tokenDTO);
    }


    @GetMapping("/query/meme")
    public Result<List<MemeTokenDTO>> memeTokenQuery(@RequestParam(required = false) String status, @RequestParam Boolean launchOnPancake, @RequestParam(required = false) String orderByField,@RequestParam(required = false) String orderByRule) {
        List<MemeTokenDTO> tokenDTOS = tokenService.memeTokenQuery(status, launchOnPancake, orderByField, orderByRule);
        return ResultUtils.success(tokenDTOS);
    }

    @PostMapping("/meme/queryByAddress")
    public Result<List<MemeTokenDTO>> queryByAddress(@RequestBody List<String> addRessList) {
        List<MemeTokenDTO> tokenDTOS = tokenService.queryByAddress(addRessList);
        return ResultUtils.success(tokenDTOS);
    }

}
