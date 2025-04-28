package com.shop.shoppingmall.global.exception;

import com.shop.shoppingmall.global.exception.custom.CategoryNotFoundException;
import com.shop.shoppingmall.global.exception.custom.TradePostNotFoundException;
import com.shop.shoppingmall.global.exception.custom.UnauthorizedAccessException;
import com.shop.shoppingmall.global.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //거래 게시물이 DB에 존재하지 않을 때
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

    //카테고리가 DB에 존재하지 않을 때
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

    //로그인이 필요한 요청에 대해 비로그인 이용자가 요청했을 경우
    //필터에서 터지는 예외 이므로 따로 처리
//    @ExceptionHandler(UnauthorizedAccessException.class)
//    public ResponseEntity<CommonResponse<Object>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
//        return ResponseEntity
//                .status(HttpStatus.UNAUTHORIZED)
//                .body(
//                        CommonResponse.builder()
//                                .code(HttpStatus.UNAUTHORIZED.value())
//                                .data(null)
//                                .message(ex.getMessage())
//                                .build()
//                );
//    }
}
