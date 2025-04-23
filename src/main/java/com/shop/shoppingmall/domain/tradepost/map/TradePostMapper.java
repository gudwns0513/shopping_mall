package com.shop.shoppingmall.domain.tradepost.map;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
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

    public TradePostSummaryResponse toDto (TradePost tradePost) {
        return TradePostSummaryResponse.builder()
                .tradePostId(tradePost.getId())
                .title(tradePost.getTitle())
                .price(tradePost.getPrice())
                .status(tradePost.getStatus())
                .createdAt(tradePost.getCreatedAt())
                .build();
    }
}
