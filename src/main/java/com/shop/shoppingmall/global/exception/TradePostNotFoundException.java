package com.shop.shoppingmall.global.exception;

public class TradePostNotFoundException extends RuntimeException {

    public TradePostNotFoundException(Long tradePostId) {
        super("해당 TradePost가 존재하지 않습니다. (tradePostId: " + tradePostId + ")");
    }
}
