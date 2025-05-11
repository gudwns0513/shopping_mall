package com.shop.shoppingmall.domain.tradehistory.service;

import com.shop.shoppingmall.domain.tradehistory.map.TradeHistoryMapper;
import com.shop.shoppingmall.domain.tradehistory.repository.TradeHistoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.map.TradePostMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeHistoryService {

    private final TradeHistoryRepository tradeHistoryRepository;

    private final TradeHistoryMapper tradeHistoryMapper;

    public void recordTradeCompletion(TradePost tradePost) {
        tradeHistoryRepository.save(
                tradeHistoryMapper.toEntity(tradePost)
        );
    }
}
