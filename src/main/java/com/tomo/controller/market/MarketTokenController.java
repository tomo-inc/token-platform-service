package com.tomo.controller.market;

import com.tomo.model.req.MarketTokenCategoryReq;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.model.resp.MarketTokenDetailInfo;
import com.tomo.model.resp.MarketTokenHistory;
import com.tomo.model.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/market/token/")
@Slf4j
public class MarketTokenController {

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public Result<List<MarketTokenBaseInfo>> list(@RequestBody List<MarketTokenReq> list) {
        return null;
    }

    @RequestMapping(value = "details",method = RequestMethod.POST)
    public Result<List<MarketTokenDetailInfo>> details(@RequestBody List<MarketTokenReq> list) {
        return null;
    }

    @RequestMapping(value = "category",method = RequestMethod.POST)
    public Result<List<MarketTokenBaseInfo>> category(@RequestBody MarketTokenCategoryReq req) {
        return null;
    }
    @RequestMapping(value = "history",method = RequestMethod.GET)
    public Result<List<MarketTokenHistory>> history(@RequestParam Long chainIndex, @RequestParam String address) {
        return null;
    }



}
