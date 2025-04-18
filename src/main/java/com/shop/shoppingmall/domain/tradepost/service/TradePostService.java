package com.shop.shoppingmall.domain.tradepost.service;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
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

    public void registerTradePost(TradePostRegisterRequest requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 DB에 존재하지 않습니다."));

        TradePost tradePost = tradePostMapper.toEntity(requestDto, category);
        tradePostRepository.save(tradePost);
    }

    public void updateTradePost(Long id, TradePostUpdateRequest requestDto) {
        TradePost tradePost = tradePostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정하려는 거래 게시물이 존재하지 않습니다."));

        Category category = null;
        if(requestDto.getCategoryId() != null) {
            category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 DB에 존재하지 않습니다."));
        }

        tradePost.updateTradePost(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                category
        );
    }

    public void deleteTradePost(Long id) {
        TradePost tradePost = tradePostRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 이미 존재하지 않습니다."));
        tradePostRepository.delete(tradePost);
    }
}
