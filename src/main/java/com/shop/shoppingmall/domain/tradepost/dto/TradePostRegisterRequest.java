package com.shop.shoppingmall.domain.tradepost.dto;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class TradePostRegisterRequest {

    @NotBlank(message = "제목을 작성해주세요.")
    private String title;

    @NotBlank(message = "상품 소개를 작성해주세요.")
    private String description;

    @Min(message = "가격을 설정해주세요.", value = 0)
    private int price;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Long categoryId;

//    public TradePost toEntity(Category category) {
//        return TradePost.builder()
//                .title(this.title)
//                .description(this.description)
//                .price(this.price)
//                .status(TradePostStatus.AVAILABLE)
//                .category(category)
//                .build();
//    }
}
