package com.shop.shoppingmall.global.websocket;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpSession session = servletRequest.getServletRequest().getSession(false);

            if (session == null) {
                System.out.println("🚫 WebSocket 연결 시도: HttpSession이 존재하지 않습니다.");
                return false; // 연결 차단 (필요 시 true로 바꾸고 권한 없는 사용자로 처리)
            }

            Principal principal = servletRequest.getServletRequest().getUserPrincipal();

            if (principal == null) {
                System.out.println("🚫 WebSocket 연결 시도: 인증된 사용자가 아닙니다 (Principal이 null).");
                return false;
            }

            attributes.put("principal", principal); // WebSocket 세션에 Principal 저장
            System.out.println("✅ WebSocket 연결 성공: 사용자 ID = " + principal.getName());
        }
        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {
        // 필요 시 후처리 로그 작성
    }
}
