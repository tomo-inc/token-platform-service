package com.tomo.controller.market;

import com.tomo.model.ResultUtils;
import com.tomo.model.req.MarketTokenReq;
import com.tomo.model.resp.MarketRiskDetail;
import com.tomo.model.resp.Result;
import com.tomo.service.market.MarketRiskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/market/risk/")
@Slf4j
public class MarketRiskController {

    @Autowired
    MarketRiskService marketRiskService;

    @RequestMapping(value = "details", method = RequestMethod.POST)
    public Result<List<MarketRiskDetail>> list(@Valid @NotEmpty @RequestBody List<MarketTokenReq> list) {
        List<MarketRiskDetail> resultList = marketRiskService.list(list);
        return ResultUtils.success(resultList);
    }
}
