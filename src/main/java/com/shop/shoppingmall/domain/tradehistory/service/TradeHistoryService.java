package com.shop.shoppingmall.domain.tradehistory.service;

import com.shop.shoppingmall.domain.tradehistory.map.TradeHistoryMapper;
import com.shop.shoppingmall.domain.tradehistory.repository.TradeHistoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TradeHistoryService {

    private final TradeHistoryRepository tradeHistoryRepository;

    private final TradeHistoryMapper tradeHistoryMapper;

    //거래 완료 시 자동 저장
    public void recordTradeCompletion(TradePost tradePost) {
        tradeHistoryRepository.save(
                tradeHistoryMapper.toEntity(tradePost)
        );
    }
}
