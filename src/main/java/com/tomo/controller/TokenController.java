package com.tomo.controller;

import com.tomo.model.ResultUtils;
import com.tomo.model.dto.TokenDTO;
import com.tomo.model.resp.Result;
import com.tomo.service.token.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/wallet/token")
public class TokenController {
    @Autowired
    TokenService tokenService;

    @GetMapping("/search")
    public Result<List<TokenDTO>> queryKLine(@RequestHeader("authorization") String authorization, @RequestParam String content, @RequestParam(required = false) String chain) {
        List<TokenDTO> dataList  = new ArrayList<>();
        List<TokenDTO> tokenDTOS = tokenService.tokenSearch(authorization, content, chain);
        if(CollectionUtils.isEmpty(tokenDTOS)) {
            Date now = new Date();
            TokenDTO tokenDTO = TokenDTO.builder()
                    .fourMemeToken(true)
                    .id(10000L)
                    .name("BSC-"+content)
                    .displayName("temp-test")
                    .symbol("temp-test")
                    .imageUrl("https://coin-images.coingecko.com/coins/images/15768/large/dogecoin.png?1696515392")
                    .decimals(8)
                    .chain("BSC")
                    .isNative(false)
                    .address(content)
                    .priceUsd(BigDecimal.valueOf(0.25622823))
                    .priceChangeH24(5.26d)
                    .volumeH24(BigDecimal.valueOf(2854274.720941230000000000))
                    .marketCapUsd(BigDecimal.valueOf(656591172))
                    .risk("risk")
                    .createdTime(now)
                    .updatedTime(now)
                    .build();
            dataList.add(tokenDTO);
        }else{
            dataList.addAll(tokenDTOS);
        }
        return ResultUtils.success(dataList);
    }

}
