package com.tomo.feign;

import com.tomo.model.resp.OKXDatumResp;
import com.tomo.model.resp.OKXResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "okx-feign",
             url = "https://www.okx.com",
             configuration = OKXClientConfig.class
)
public interface OKXClient {
    // all 是无关链，其他的传链id
    @GetMapping("/priapi/v1/dx/market/v2/advanced/ranking/content")
    OKXResult<OKXDatumResp> listTrendingTokens(@RequestParam("chainId") String chainId, @RequestParam("t") Long timestamp);

    @GetMapping("/priapi/v1/dx/trade/multi/allTokens")
    OKXResult<OKXDatumResp> listAllTokens(@RequestParam String chainId);

    @GetMapping("/api/v5/market/candles")
    OKXResult<List<List<Double>>> marketCandles(@RequestParam("instId") String coinId, @RequestParam("bar") String bar);

    @GetMapping("/api/v5/market/index-candles")
    OKXResult<List<List<Double>>> marketIndexCandles(@RequestParam("instId") String coinId, @RequestParam("bar") String bar);
}
