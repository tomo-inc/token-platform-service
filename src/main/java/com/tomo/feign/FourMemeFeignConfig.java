package com.tomo.feign;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FourMemeFeignConfig {
    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(1500, 5000);
    }
}
