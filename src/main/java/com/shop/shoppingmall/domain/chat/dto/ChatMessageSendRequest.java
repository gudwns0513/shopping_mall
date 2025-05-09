package com.shop.shoppingmall.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ChatMessageSendRequest {

    private Long chatRoomId;

    private String content;
}
