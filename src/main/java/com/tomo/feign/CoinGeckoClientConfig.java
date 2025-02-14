package com.tomo.feign;


import feign.Request;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class CoinGeckoClientConfig {
    @Value("${coingecko.api-key:CG-QFuLrVNPbZ4VoF4rgDXkCUAb}")
    private String apiKey ;


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-cg-pro-api-key", apiKey);
        };
    }

    @Bean
    public Request.Options options() {
        // 连接超时时间，单位为毫秒
        int connectTimeoutMillis = 5000;
        // 读取超时时间，单位为毫秒
        int readTimeoutMillis = 10000;
        return new Request.Options(connectTimeoutMillis, readTimeoutMillis);
    }
}