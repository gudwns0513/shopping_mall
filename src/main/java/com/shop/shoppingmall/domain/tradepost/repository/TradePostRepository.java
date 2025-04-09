package com.shop.shoppingmall.domain.tradepost.repository;

import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long> {
}
