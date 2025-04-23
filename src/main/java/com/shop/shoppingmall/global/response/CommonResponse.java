package com.shop.shoppingmall.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
public class CommonResponse<T> {

    private final int code;

    private final T data;

    private final String message;
}
