package com.shop.shoppingmall.domain.chat.controller;

import com.shop.shoppingmall.domain.chat.dto.ChatRoomDetailResponse;
import com.shop.shoppingmall.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/{tradePostId}")
    public ResponseEntity<ChatRoomDetailResponse> getOrCreateChatRoom(
            @PathVariable Long tradePostId,
            @AuthenticationPrincipal OAuth2User oAuth2User) {

        Long buyerId = (Long)oAuth2User.getAttributes().get("id");

        // 채팅방이 있는지 확인
        ChatRoomDetailResponse response = chatRoomService.findOrCreateChatRoom(tradePostId, buyerId);

        // 채팅방 ID 반환
        return ResponseEntity.ok(response);
    }
}
