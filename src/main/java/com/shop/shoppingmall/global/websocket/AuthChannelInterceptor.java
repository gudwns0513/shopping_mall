package com.shop.shoppingmall.global.websocket;

import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthChannelInterceptor implements ChannelInterceptor {

    private final UserRepository userRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) return message;

        // STOMP CONNECT 명령을 처리
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            List<String> authHeaders = accessor.getNativeHeader("Authorization");
            if (authHeaders != null && !authHeaders.isEmpty()) {
                String header = authHeaders.get(0); // 예: "JSESSIONID=abc123"
                if (header.startsWith("JSESSIONID=")) {
                    String jsessionId = header.substring("JSESSIONID=".length());
                    System.out.println("STOMP CONNECT에서 받은 JSESSIONID: " + jsessionId);

                    // 1. JSESSIONID로 DB에서 사용자 정보 조회
                    User user = userRepository.findByJsessionId(jsessionId)
                            .orElseThrow(() -> new IllegalArgumentException("인증된 사용자가 아닙니다."));

                    // 2. WebSocket 세션에 Principal로 사용자 정보 설정
                    accessor.setUser(() -> String.valueOf(user.getId()));  // Principal에 사용자 ID 설정
                } else {
                }
            } else {
                System.out.println("STOMP CONNECT 요청에 Authorization 헤더 없음");
            }
        }

        return message;
    }
}
