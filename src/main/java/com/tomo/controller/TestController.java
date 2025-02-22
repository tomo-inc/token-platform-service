package com.tomo.controller;

import com.tomo.feign.FourMemeClient;
import com.tomo.model.ResultUtils;
import com.tomo.model.dto.MemeTokenDTO;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.Result;
import com.tomo.model.resp.TokenInfoRes;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private FourMemeClient fourMemeClient;

    @GetMapping("/token/image")
    public Result<List<TokenInfoRes>> tokenImage(@RequestParam String tokenAddress) {
        Result<List<TokenInfoRes>> result = fourMemeClient.tokenQuery(tokenAddress, 1, 1);

        return ResultUtils.success(result.getData());
    }
}
