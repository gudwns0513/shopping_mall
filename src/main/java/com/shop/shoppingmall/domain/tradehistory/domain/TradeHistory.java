package com.shop.shoppingmall.domain.tradehistory.domain;

import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.global.entity.CreatedAtBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class TradeHistory extends CreatedAtBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false, updatable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private TradePost tradePost;
}
