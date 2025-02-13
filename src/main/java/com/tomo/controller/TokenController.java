package com.tomo.controller;

import com.tomo.model.ResultUtils;
import com.tomo.model.dto.MemeTokenDTO;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.req.MemeTokenQueryReq;
import com.tomo.model.resp.Result;
import com.tomo.service.token.TokenService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

    @GetMapping("/query/meme")
    public Result<List<MemeTokenDTO>> memeTokenQuery(@RequestParam String status, @RequestParam Boolean launchOnPancake ) {
        List<MemeTokenDTO> tokenDTOS = tokenService.memeTokenQuery(status, launchOnPancake);
        return ResultUtils.success(tokenDTOS);
    }

}
