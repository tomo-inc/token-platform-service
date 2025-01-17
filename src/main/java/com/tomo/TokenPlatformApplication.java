package com.tomo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.tomo.mapper")
@EnableFeignClients
@SpringBootApplication
public class TokenPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenPlatformApplication.class, args);
    }

}
