package com.shop.shoppingmall.global.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long categoryId) {
        super("해당 Category가 존재하지 않습니다. (categoryId: " + categoryId + ")");
    }
}
