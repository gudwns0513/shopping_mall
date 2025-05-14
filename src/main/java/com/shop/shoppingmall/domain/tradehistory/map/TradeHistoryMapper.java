package com.shop.shoppingmall.domain.tradehistory.map;

import com.shop.shoppingmall.domain.tradehistory.domain.TradeHistory;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import org.springframework.stereotype.Component;

@Component
public class TradeHistoryMapper {

    public TradeHistory toEntity(TradePost tradePost) {
        return TradeHistory.builder()
                .seller(tradePost.getUser())
                .tradePost(tradePost)
                .build();
    }
}
