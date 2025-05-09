package com.shop.shoppingmall.global.exception.custom;

public class ChatRoomNotFoundException extends RuntimeException {

    public ChatRoomNotFoundException(Long chatRoomId) {
        super("해당 ChatRoom이 존재하지 않습니다. (chatRoomId: " + chatRoomId + ")");
    }
}
