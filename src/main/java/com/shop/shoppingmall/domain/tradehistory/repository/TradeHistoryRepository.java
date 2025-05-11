package com.shop.shoppingmall.domain.trade.repository;

import com.shop.shoppingmall.domain.trade.domain.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
}
