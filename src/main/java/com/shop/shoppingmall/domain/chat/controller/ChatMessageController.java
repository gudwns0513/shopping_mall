package com.shop.shoppingmall.domain.chat.controller;

import com.shop.shoppingmall.domain.chat.dto.ChatMessageSendRequest;
import com.shop.shoppingmall.domain.chat.dto.ChatMessageDetailResponse;
import com.shop.shoppingmall.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;


    // 클라이언트가 메시지를 전송할 때 호출되는 메서드
    @MessageMapping("/chat/send/{chatRoomId}")
    @SendTo("/sub/chat/room/{chatRoomId}")
    public ChatMessageDetailResponse sendMessage(
            ChatMessageSendRequest chatMessage,
            Principal principal,
            @DestinationVariable("chatRoomId") Long chatRoomId
    ) {
        Long senderId = Long.parseLong(principal.getName());
        return chatMessageService.saveMessage(chatMessage, senderId);
    }

    //웹소켓 1대1 채팅 테스트
    @MessageMapping("/chat/ex/{chatRoomId}")
    @SendTo("/sub/chat/ex/{chatRoomId}")
    public ChatMessageDetailResponse ex(
            ChatMessageSendRequest chatMessage,
            @DestinationVariable("chatRoomId") Long chatRoomId
    ) {
        return ChatMessageDetailResponse.builder()
                .content(chatMessage.getContent())
                .chatRoomId(chatRoomId)
                .senderNickname("김형준")
                .senderId(1L)
                .build();
    }

}
