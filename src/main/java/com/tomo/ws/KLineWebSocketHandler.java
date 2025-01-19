package com.tomo.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tomo.model.TokenBase;
import com.tomo.model.dto.TokenOhlcvDTO;
import com.tomo.model.ws.TokenPriceWsReceive;
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

@Slf4j
public class KLineWebSocketHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, TokenBase> session = new ConcurrentHashMap<>();
    private static final int MAX_CONNECTIONS = 10000;

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session.put(session, new TokenBase());
        executorService.scheduleAtFixedRate(() -> {
            try {
                log.info("executorService.scheduleAtFixedRate");
                TokenBase token = this.session.get(session);
                if (!TokenBase.isNull(token)) {
                    List<TokenOhlcvDTO> list = new ArrayList<>();
                    session.sendMessage(new TextMessage(JsonService.toJson(list)));
                }
            } catch (IOException e) {
                log.error("KLineWebSocketHandler afterConnectionEstablished", e);
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("KLineWebSocketHandler afterConnectionClosed");
        this.session.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String payload = message.getPayload();
            TokenBase tokenBase = JsonService.fromJson(payload, new TypeReference<TokenBase>() {});
            this.session.put(session, tokenBase);
        }catch (Exception e){
            log.error("TokenPriceWebSocketHandler handleTextMessage", e);
            this.session.remove(session);
        }
    }

    public boolean checkConnectionLimit() {
        return session.size() < MAX_CONNECTIONS;
    }

    private double getTokenPrice(TokenPriceWsReceive token) {
        // 这里可以调用外部 API 或服务来获取真实的代币价格
        // 为了演示，简单返回一个随机价格
        return Math.random() * 100;
    }
}
