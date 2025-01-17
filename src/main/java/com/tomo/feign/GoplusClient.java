package com.tomo.feign;

import com.tomo.model.resp.SolanaSecurityResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "goplus", url = "https://api.gopluslabs.io/api/v1")
public interface GoplusClient {
    @GetMapping("/solana/token_security")
    SolanaSecurityResp getSolanaSecurity(@RequestParam("contract_addresses") String contractAddresses,
                                         @RequestHeader("Authorization") String authorization);
}
