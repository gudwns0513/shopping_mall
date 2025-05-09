package com.shop.shoppingmall.domain.chat.map;

import com.shop.shoppingmall.domain.chat.domain.ChatMessage;
import com.shop.shoppingmall.domain.chat.domain.ChatRoom;
import com.shop.shoppingmall.domain.chat.dto.ChatMessageDetailResponse;
import com.shop.shoppingmall.domain.user.domain.User;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    public ChatMessage toEntity(String content, ChatRoom chatRoom, User sender) {
        return ChatMessage.builder()
                .content(content)
                .chatRoom(chatRoom)
                .sender(sender)
                .build();
    }

    public ChatMessageDetailResponse toDetailResponse(ChatMessage chatMessage) {
        return ChatMessageDetailResponse.builder()
                .content(chatMessage.getContent())
                .chatRoomId(chatMessage.getChatRoom().getId())
                .senderId(chatMessage.getSender().getId())
                .senderNickname(chatMessage.getSender().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
