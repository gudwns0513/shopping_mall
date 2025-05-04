package com.shop.shoppingmall.domain.chat.controller;

import com.shop.shoppingmall.domain.chat.domain.ChatMessage;
import com.shop.shoppingmall.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    // 클라이언트가 메시지를 전송할 때 호출되는 메서드
    @MessageMapping("/chat/send")
    @SendTo("/sub/chat/room/{chatRoomId}")  // 구독 경로로 메시지를 전송
    public ChatMessage sendMessage(
            ChatMessage chatMessage,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {
        Long userId = (Long)oAuth2User.getAttributes().get("id");

        // 메시지 저장
        chatMessageService.saveMessage(chatMessage);

        // 메시지를 반환하면 자동으로 /sub/chat/room/{chatRoomId} 경로로 발행됨
        return chatMessage;
    }
}
