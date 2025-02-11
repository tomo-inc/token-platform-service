package com.tomo.feign;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackendFeignConfig {
    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(1500, 3500);
    }
}
