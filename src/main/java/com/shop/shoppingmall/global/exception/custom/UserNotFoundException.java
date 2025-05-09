package com.shop.shoppingmall.global.exception.custom;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("해당 User가 존재하지 않습니다. (userId: "  + userId + ")");
    }
}
