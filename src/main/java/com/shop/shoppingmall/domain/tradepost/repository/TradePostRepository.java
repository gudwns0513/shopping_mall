package com.shop.shoppingmall.domain.tradepost.repository;

import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long> {
    Slice<TradePost> findTradePostByCategoryId(Long categoryId, Pageable pageable);
}
