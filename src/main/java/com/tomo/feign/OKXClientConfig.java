package com.tomo.feign;


import feign.RequestInterceptor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OKXClientConfig {
    @Value("${okx.api-key:cbc24600-03aa-497b-8eb9-0737473083d1}")
    private String apiKey ;

    @Value("${okx.secret-key:325B7119BC5A51FDA7F44E86FF0528AB}")
    private String secretKey;

    @Value("${okx.passphrase:@Ankr1234}")
    private String passphrase;

    @Value("${okx.project:4c49d3f756f4de6270c09c37c76a94d4}")
    private String project;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String method = requestTemplate.method();
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
            String isoString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
            String path = requestTemplate.path() + requestTemplate.queryLine();
            String requestBody = null;
            if (requestTemplate.body() != null) {
                requestBody = new String(requestTemplate.body(), StandardCharsets.UTF_8);
            }
            StringBuilder signBuilder = new StringBuilder(isoString).append(method).append(path);
            if (requestBody != null) {
                signBuilder.append(requestBody);
            }
            String sign = Base64.encodeBase64String(getHmacSha256Str(signBuilder.toString(), secretKey));
            requestTemplate.header("OK-ACCESS-SIGN", sign);
            requestTemplate.header("OK-ACCESS-TIMESTAMP", isoString);
            requestTemplate.header("OK-ACCESS-KEY", apiKey);
            requestTemplate.header("OK-ACCESS-PASSPHRASE", passphrase);
            requestTemplate.header("OK-ACCESS-PROJECT", project);
        };
    }

    public static byte[] getHmacSha256Str(String data, String key) {
        try {
            String algorithm = "HmacSHA256";
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return null;
        }
    }
}