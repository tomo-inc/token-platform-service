package com.tomo.feign;

import com.tomo.model.dto.DefiLamaIconDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "defiLama-client", url = "https://api.llama.fi/protocols", configuration = BackendFeignConfig.class)
public interface DefiLamaClient {
    @GetMapping
    List<DefiLamaIconDto> queryIcon();

}
