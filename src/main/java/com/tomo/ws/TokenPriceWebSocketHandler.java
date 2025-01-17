package com.tomo.ws;

import com.tomo.model.dto.TokenInfoDTO;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TokenPriceWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private static final int MAX_CONNECTIONS = 10000;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public boolean checkConnectionLimit(){
        return sessions.size() < MAX_CONNECTIONS;
    }

    public void broadcastTokenPrice(TokenInfoDTO tokenPriceResp) {
        String json = "";
        // 这里将 TokenPriceResp 对象转换为 JSON 字符串
        // 假设使用 Jackson 库
        // import com.fasterxml.jackson.databind.ObjectMapper;
        // ObjectMapper objectMapper = new ObjectMapper();
        // json = objectMapper.writeValueAsString(tokenPriceResp);
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                // 处理异常，例如记录日志或采取其他操作
            }
        }
    }
}