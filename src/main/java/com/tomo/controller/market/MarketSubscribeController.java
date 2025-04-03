package com.tomo.controller.market;

import com.tomo.model.ResultUtils;
import com.tomo.model.req.MarketSubscribeReq;
import com.tomo.model.resp.MarketSubscribeResp;
import com.tomo.model.resp.Result;
import com.tomo.service.market.MarketSubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/market/subscribe/")
public class MarketSubscribeController {

    @Autowired
    private MarketSubscribeService marketSubscribeService;

    @RequestMapping(value = "batchSubscribe",method = RequestMethod.POST)
    public Result<Void> subscribe(List<MarketSubscribeReq> req) {
        marketSubscribeService.batchSubscribe(req);
        return ResultUtils.success(null);
    }

    @RequestMapping(value = "unSubscribe",method = RequestMethod.POST)
    public Result<Void> unSubscribe(List<MarketSubscribeReq> req) {
        marketSubscribeService.unSubscribe(req);
        return ResultUtils.success(null);
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result<List<MarketSubscribeResp>> list(@RequestParam(value = "chainIndex",required = false) Long chainIndex,
                                                  @RequestParam(value = "address",required = false) String address,
                                                  @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize) {
        return ResultUtils.success(marketSubscribeService.list(chainIndex, address, pageNum, pageSize));
    }


}
