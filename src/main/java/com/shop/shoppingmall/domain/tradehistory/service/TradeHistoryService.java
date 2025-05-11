package com.shop.shoppingmall.domain.trade.service;

import com.shop.shoppingmall.domain.trade.repository.TradeHistoryRepository;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeHistoryService {

    private final TradeHistoryRepository tradeHistoryRepository;

    public void completeTrade(Long id, TradePostUpdateRequest requestDto) {

    }
}
