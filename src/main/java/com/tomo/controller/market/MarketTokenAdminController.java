package com.tomo.controller.market;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tomo.model.ResultUtils;
import com.tomo.model.dto.MarketTokenDTO;
import com.tomo.model.req.MarketTokenQueryReq;
import com.tomo.model.resp.Result;
import com.tomo.service.market.MarketTokenAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/market/admin")
public class MarketTokenAdminController {

    @Autowired
    private MarketTokenAdminService marketTokenAdminService;

    @PostMapping("/updateMarketToken")
    public Result updateMarketToken(@RequestBody MarketTokenDTO tokenDTO) {

        marketTokenAdminService.updateMarketToken(tokenDTO);
        return ResultUtils.success("ok");
    }

    @PostMapping("/getMarketTokenList")
    public Result<IPage<MarketTokenDTO>> pageMarketToken(@RequestBody MarketTokenQueryReq queryDTO) {
        IPage<MarketTokenDTO> pageResult = marketTokenAdminService.pageMarketToken(queryDTO);
        return ResultUtils.success(pageResult);
    }
}
