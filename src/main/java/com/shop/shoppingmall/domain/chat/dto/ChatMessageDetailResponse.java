package com.shop.shoppingmall.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ChatMessageDetailResponse {

    private String content;

    private Long senderId;

    private String senderNickname;

    private Long chatRoomId;

    private LocalDateTime createdAt;
}
