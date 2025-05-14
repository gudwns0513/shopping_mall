package com.shop.shoppingmall.global.exception.custom;

public class TradePostModificationNotAllowedException extends RuntimeException {

    public TradePostModificationNotAllowedException(Long tradePostId) {
        super("이미 거래가 완료된 게시물은 수정할 수 없습니다. (tradePostId: " + tradePostId + ")");
    }
}
