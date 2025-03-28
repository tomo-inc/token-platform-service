package com.tomo.controller.market;

import com.tomo.model.resp.MarketOHLCVInfo;
import com.tomo.model.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/market/ohlcv/")
public class MarketKLineController {

    @RequestMapping(value = "history",method = RequestMethod.GET)
    public Result<MarketOHLCVInfo> getKLine(@RequestParam Long chainIndex, @RequestParam String address, @RequestParam String interval) {
        return null;
    }
}
