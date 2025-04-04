package com.tomo.controller.market;

import cn.hutool.db.Page;
import com.tomo.model.ResultUtils;
import com.tomo.model.req.MarketTokenCategoryReq;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketTokenBaseInfo;
import com.tomo.model.resp.MarketTokenDetailInfo;
import com.tomo.model.resp.MarketTokenHistory;
import com.tomo.model.resp.Result;
import com.tomo.service.market.MarketTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/market/token/")
@Slf4j
public class MarketTokenController {

    @Autowired
    private MarketTokenService marketTokenService;

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result<List<MarketTokenBaseInfo>> list(@Valid @NotEmpty @RequestBody List<MarketTokenReq> list) {
        List<MarketTokenBaseInfo> resultList = marketTokenService.queryBaseInfoList(list);
        return ResultUtils.success(resultList);
    }

    @RequestMapping(value = "details", method = RequestMethod.POST)
    public Result<List<MarketTokenDetailInfo>> details(@Valid @NotEmpty @RequestBody List<MarketTokenReq> list) {
        List<MarketTokenDetailInfo> resultList = marketTokenService.details(list);
        return ResultUtils.success(resultList);
    }

    @RequestMapping(value = "category", method = RequestMethod.POST)
    public Result<List<MarketTokenDetailInfo>> category(@Valid @RequestBody MarketTokenCategoryReq req) {
        List<MarketTokenDetailInfo> resultList = marketTokenService.category(req);
        return ResultUtils.success(resultList);
    }

    @RequestMapping(value = "history", method = RequestMethod.GET)
    public Result<List<MarketTokenHistory>> history(@NotNull @RequestParam Long chainIndex,
                                                    @RequestParam(required = false) String address,
                                                    @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<MarketTokenHistory> resultList = marketTokenService.history(chainIndex, address, pageNum, pageSize);
        return ResultUtils.success(resultList);
    }


}
