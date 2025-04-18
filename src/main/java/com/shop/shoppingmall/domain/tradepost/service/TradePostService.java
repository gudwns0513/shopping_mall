package com.shop.shoppingmall.domain.tradepost.service;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.map.TradePostMapper;
import com.shop.shoppingmall.domain.tradepost.repository.TradePostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradePostService {

    private final TradePostRepository tradePostRepository;

    private final CategoryRepository categoryRepository;

    private final TradePostMapper tradePostMapper;

    public void registerTradePost(TradePostRegisterRequest tradePostRegisterRequest) {
        Category category = categoryRepository.findById(tradePostRegisterRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 DB에 존재하지 않습니다."));

        TradePost tradePost = tradePostMapper.toEntity(tradePostRegisterRequest, category);
        tradePostRepository.save(tradePost);
    }
}
