package com.shop.shoppingmall.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ChatRoomDetailResponse {

    private Long chatRoomId;

    private Long buyerId;

    private Long sellerId;

    private Long tradePostId;
}
