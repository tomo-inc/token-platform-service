package com.tomo.feign;

import com.tomo.model.dto.TokenDTO;
import com.tomo.model.req.BitqueryReq;
import com.tomo.model.resp.BackendResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "backend-client", url = "${backend.client.url}", configuration = BackendFeignConfig.class)
public interface BackendClient {

    @GetMapping("/api/socialLogin/teleGram/wallet/tokens/search")
    BackendResponseDTO<List<TokenDTO>> tokenSearch(@RequestHeader("authorization") String authorization, @RequestParam String content, @RequestParam(required = false) String chain);


    @GetMapping("/api/socialLogin/teleGram/getByName")
    BackendResponseDTO<TokenDTO> tokenDetail(@RequestHeader("authorization") String authorization, @RequestParam String tokenName);

}
