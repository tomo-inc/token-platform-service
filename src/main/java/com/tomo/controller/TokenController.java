package com.tomo.controller;

import com.tomo.model.ResultUtils;
import com.tomo.model.dto.TokenDTO;
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

}
