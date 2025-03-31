package com.tomo.controller.market;

import com.tomo.model.ResultUtils;
import com.tomo.model.resp.MarketOHLCVInfo;
import com.tomo.model.resp.Result;
import com.tomo.service.market.MarketKLineService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/market/ohlcv/")
public class MarketKLineController {

    @Autowired
    private MarketKLineService marketKLineService;

    @RequestMapping(value = "history", method = RequestMethod.GET)
    public Result<List<MarketOHLCVInfo>> getKLine(@NotNull @RequestParam Long chainIndex,
                                 @RequestParam(required = false) String address,
                                 @NotBlank @RequestParam String interval) {
        List<MarketOHLCVInfo> list = marketKLineService.getKLine(chainIndex, address, interval);
        return ResultUtils.success(list);
    }
}
