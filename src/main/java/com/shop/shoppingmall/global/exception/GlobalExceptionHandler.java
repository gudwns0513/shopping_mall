package com.shop.shoppingmall.global.exception;

import com.shop.shoppingmall.global.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TradePostNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleTradePostNotFoundException(TradePostNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        CommonResponse.builder()
                                .code(HttpStatus.NOT_FOUND.value())
                                .data(null)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<CommonResponse<Object>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        CommonResponse.builder()
                                .code(HttpStatus.NOT_FOUND.value())
                                .data(null)
                                .message(ex.getMessage())
                                .build()
                );
    }
}
