//package com.tomo.ws;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//@Slf4j
//public class ConnectionLimitInterceptor implements HandshakeInterceptor {
//    private final TokenPriceWebSocketHandler tokenPriceWebSocketHandler;
//
//    public ConnectionLimitInterceptor(TokenPriceWebSocketHandler tokenPriceWebSocketHandler) {
//        this.tokenPriceWebSocketHandler = tokenPriceWebSocketHandler;
//    }
//
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        if (!tokenPriceWebSocketHandler.checkConnectionLimit()) {
//            response.setStatusCode(HttpStatusCode.valueOf(429));
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
//
//    }
//}
