package com.shop.shoppingmall.domain.tradepost.dto;

import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class TradePostDetailResponse {

    private Long tradePostId;

    private String title;

    private String description;

    private int price;

    private TradePostStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
