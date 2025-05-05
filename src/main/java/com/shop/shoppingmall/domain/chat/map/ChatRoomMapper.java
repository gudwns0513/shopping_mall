package com.shop.shoppingmall.domain.chat.map;

import com.shop.shoppingmall.domain.chat.domain.ChatRoom;
import com.shop.shoppingmall.domain.chat.dto.ChatRoomDetailResponse;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {

    //ChatRoom 엔티티 생성
    public ChatRoom toEntity(TradePost tradePost, User buyer) {
        return ChatRoom.builder()
                .seller(tradePost.getUser())
                .buyer(buyer)
                .tradePost(tradePost)
                .build();
    }

    //DTO 변환
    public ChatRoomDetailResponse toDetailResponse(ChatRoom chatRoom) {
        return ChatRoomDetailResponse.builder()
                .chatRoomId(chatRoom.getId())
                .buyerId(chatRoom.getBuyer().getId())
                .sellerId(chatRoom.getSeller().getId())
                .tradePostId(chatRoom.getTradePost().getId())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
