package com.shop.shoppingmall.domain.tradepost.map;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class TradePostMapper {

    public TradePost toEntity(TradePostRegisterRequest dto, Category category) {
        return TradePost.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();
    }
}
