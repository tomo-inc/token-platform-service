package com.tomo.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tokenPriceWebSocketHandler(), "/tokenPrice");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 设置最大文本消息大小为 1MB
        container.setMaxTextMessageBufferSize(1024 * 1024);
        // 设置最大二进制消息大小为 1MB
        container.setMaxBinaryMessageBufferSize(1024 * 1024);
        // 设置最大会话空闲超时时间为 3 分钟
        container.setMaxSessionIdleTimeout(1 * 60 * 1000L);
        return container;
    }

    @Bean
    public WebSocketHandler tokenPriceWebSocketHandler() {
        return new TokenPriceWebSocketHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor(WebSocketHandler tokenPriceWebSocketHandler) {
        return new ConnectionLimitInterceptor((TokenPriceWebSocketHandler) tokenPriceWebSocketHandler);
    }

}