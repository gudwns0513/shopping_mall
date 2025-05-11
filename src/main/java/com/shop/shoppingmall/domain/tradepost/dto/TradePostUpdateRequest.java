package com.shop.shoppingmall.domain.tradepost.dto;

import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TradePostUpdateRequest {

    private String title;

    private String description;

    @Min(0)
    private Integer price;

    private Long categoryId;

    private TradePostStatus status;
}
