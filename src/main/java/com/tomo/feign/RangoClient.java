package com.tomo.feign;

import com.tomo.model.dto.DefiLamaIconDto;
import com.tomo.model.dto.RangoIconDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "rango-client", url = "https://api.rango.exchange", configuration = BackendFeignConfig.class)
public interface RangoClient {
    @GetMapping("/basic/meta/swappers?apiKey=46a05ba0-6ab1-40d9-83c9-a72ade40a50e")
    List<RangoIconDto> queryIcon();

}
