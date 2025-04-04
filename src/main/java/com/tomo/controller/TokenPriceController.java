package com.tomo.controller;

import com.tomo.service.category.CoinGeckoService;
import com.tomo.model.market.enums.IntervalEnum;
import com.tomo.model.ResultUtils;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/wallet/token-price")
public class TokenPriceController {
    @Autowired
    CoinGeckoService geckoService;

    @GetMapping("/query/k-line/history")
    public Result<List<TokenOhlcvDTO>> queryKLine(@RequestParam("chainId") String chainId,
                                                  @RequestParam(value = "address" ,required = false) String address,
                                                  @RequestParam("interval") String interval) {
        IntervalEnum intervalEnum = IntervalEnum.fromValue(interval);
        if (intervalEnum == null) {
            return ResultUtils.success(new ArrayList<>());
        }
        return ResultUtils.success(geckoService.getPlatformTokenOhlcv(Long.valueOf(chainId),address,intervalEnum));
    }

}
