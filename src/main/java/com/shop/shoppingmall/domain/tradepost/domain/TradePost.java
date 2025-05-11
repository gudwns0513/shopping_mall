package com.shop.shoppingmall.domain.tradepost.domain;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TradePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String description;

    @Column(nullable = false)
    private int price;

    private String imagePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradePostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Category category;

    public void updateTradePost(String title, String description, Integer price, TradePostStatus status, Category category) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (price != null) this.price = price;
        if (status != null) this.status = status;
        if (category != null) this.category = category;
    }
}
