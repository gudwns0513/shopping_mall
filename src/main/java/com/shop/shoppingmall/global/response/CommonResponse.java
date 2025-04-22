package com.shop.shoppingmall.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    private final int code;

    private final T data;

    private final String message;
}
