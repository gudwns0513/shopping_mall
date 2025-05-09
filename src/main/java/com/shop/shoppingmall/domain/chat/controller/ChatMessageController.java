package com.shop.shoppingmall.domain.chat.controller;

import com.shop.shoppingmall.domain.chat.dto.ChatMessageSendRequest;
import com.shop.shoppingmall.domain.chat.dto.ChatMessageDetailResponse;
import com.shop.shoppingmall.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
}
