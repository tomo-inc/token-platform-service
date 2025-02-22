package com.tomo.feign;

import feign.Request;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class FourMemeFeignConfig {
    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(1500, 5000);
    }


    private static List<String> proxyIps = new ArrayList<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    static {
        proxyIps.add("geo.g-w.info:10080");
    }


    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .proxySelector(new ProxySelector() {
                    @Override
                    public List<Proxy> select(URI uri) {
                        // 动态选择代理（示例为轮询）
                        int index = counter.getAndIncrement() % proxyIps.size();
                        String[] ipPort = proxyIps.get(index).split(":");
                        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                                new InetSocketAddress(ipPort[0], Integer.parseInt(ipPort[1])));
                        return List.of(proxy);
                    }

                    @Override
                    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                        // 处理代理连接失败（如标记失效代理）
                    }
                })
                // 在 OkHttpClient 中添加代理认证
                .proxyAuthenticator((route, response) -> {
                    String credential = Credentials.basic("49a9cyMttYLayWOT", "ralK5YmtK3wQprfF");
                    return response.request().newBuilder()
                            .header("Proxy-Authorization", credential)
                            .build();
                })
                .build();
    }
}
