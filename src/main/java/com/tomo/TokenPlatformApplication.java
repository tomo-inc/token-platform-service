package com.tomo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.tomo.mapper")
@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class TokenPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenPlatformApplication.class, args);
    }

}
