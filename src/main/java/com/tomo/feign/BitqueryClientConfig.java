package com.tomo.feign;


import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class BitqueryClientConfig {

    @Value("${bitquery.api-key:''}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("X-API-KEY", apiKey);
    }
    }