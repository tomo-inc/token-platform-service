package com.tomo.feign;

import com.tomo.model.req.BitqueryReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "bitquery-client", url = "https://streaming.bitquery.io", configuration = BitqueryClientConfig.class)
public interface BitqueryClient {

    @PostMapping("/eap")
    Map<String, Object> query(@RequestBody BitqueryReq request);

}
