package com.tomo.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomo.model.ws.TokenPriceWsReceive;
import com.tomo.model.ws.TokenPriceWsReply;
import com.tomo.service.JsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ws 推送
 */
@Slf4j
public class TokenPriceWebSocketHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, List<TokenPriceWsReceive>> sessionTokens = new ConcurrentHashMap<>();
    private static final int MAX_CONNECTIONS = 10000;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionTokens.put(session, new ArrayList<>());

        executorService.scheduleAtFixedRate(() -> {
            try {
                log.info("executorService.scheduleAtFixedRate");
                List<TokenPriceWsReceive> tokens = sessionTokens.get(session);
                if (tokens != null && !tokens.isEmpty()) {
                    // 模拟获取代币价格
                    List<TokenPriceWsReply> list = tokens.stream().map(token -> {
                        TokenPriceWsReply tokenPriceWsReply = new TokenPriceWsReply();
                        tokenPriceWsReply.setPrice(String.valueOf(getTokenPrice(token)));
                        tokenPriceWsReply.setAddress(token.getAddress());
                        tokenPriceWsReply.setChainId(token.getChainId());
                        return tokenPriceWsReply;
                    }).toList();
                    session.sendMessage(new TextMessage(JsonService.toJson(list)));
                }
            } catch (IOException e) {
                log.error("TokenPriceWebSocketHandler afterConnectionEstablished", e);
                sessionTokens.remove(session);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed");
        sessionTokens.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            List<TokenPriceWsReceive> tokens = sessionTokens.computeIfAbsent(session, k -> new ArrayList<>());
            String payload = message.getPayload();
            List<TokenPriceWsReceive> tokenPriceWsReceive = JsonService.fromJson(payload, new TypeReference<List<TokenPriceWsReceive>>() {});
            tokenPriceWsReceive.forEach(token -> {
                TokenPriceWsReceive receive = new TokenPriceWsReceive();
                receive.setAddress(token.getAddress());
                receive.setChainId(token.getChainId());
                if ("insert".equals(token.getStatus())) {
                    tokens.add(receive);     // 插入代币
                } else if ("remove".equals(token.getStatus())) {
                    tokens.remove(receive);  // 移除代币
                }
            });
        }catch (Exception e){
            log.error("TokenPriceWebSocketHandler handleTextMessage", e);
        }
    }

    public boolean checkConnectionLimit() {
        return sessionTokens.size() < MAX_CONNECTIONS;
    }

    private double getTokenPrice(TokenPriceWsReceive token) {
        // 这里可以调用外部 API 或服务来获取真实的代币价格
        // 为了演示，简单返回一个随机价格
        return Math.random() * 100;
    }
}