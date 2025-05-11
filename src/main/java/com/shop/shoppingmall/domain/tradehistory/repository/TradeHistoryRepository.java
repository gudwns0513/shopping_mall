package com.shop.shoppingmall.domain.tradehistory.repository;

import com.shop.shoppingmall.domain.tradehistory.domain.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {
}
