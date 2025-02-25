package com.tomo.feign;

import com.tomo.model.resp.Result;
import com.tomo.model.resp.TokenInfoRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "four-meme-client", url = "https://four.meme/meme-api", configuration = FourMemeFeignConfig.class)
public interface FourMemeClient {

    @GetMapping("/v1/private/token/query")
    Result<List<TokenInfoRes>> tokenQuery(@RequestParam("tokenName") String tokenName,@RequestParam("pageIndex") Integer pageIndex,@RequestParam("pageSize") Integer pageSize);


    @GetMapping("/v1/private/token/get")
    Result<TokenInfoRes> getToken(@RequestParam("address") String address);
}
