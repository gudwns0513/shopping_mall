package com.shop.shoppingmall.domain.tradepost.service;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.map.TradePostMapper;
import com.shop.shoppingmall.domain.tradepost.repository.TradePostRepository;
import com.shop.shoppingmall.global.exception.CategoryNotFoundException;
import com.shop.shoppingmall.global.exception.TradePostNotFoundException;
import com.shop.shoppingmall.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TradePostService {

    private final TradePostRepository tradePostRepository;

    private final CategoryRepository categoryRepository;

    private final TradePostMapper tradePostMapper;

    public void registerTradePost(TradePostRegisterRequest requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(requestDto.getCategoryId()));

        TradePost tradePost = tradePostMapper.toEntity(requestDto, category);
        tradePostRepository.save(tradePost);
    }

    public void updateTradePost(Long id, TradePostUpdateRequest requestDto) {
        TradePost tradePost = tradePostRepository.findById(id)
                .orElseThrow(() -> new TradePostNotFoundException(id));

        Category category = null;
        if(requestDto.getCategoryId() != null) {
            category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(requestDto.getCategoryId()));
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
                .orElseThrow(() -> new TradePostNotFoundException(id));
        tradePostRepository.delete(tradePost);
    }

    public SliceResponse<TradePostSummaryResponse> getTradePostList(Long categoryId, Pageable pageable) {
        Slice<TradePost> tradePostSlice = tradePostRepository.findTradePostByCategoryId(categoryId, pageable);

        List<TradePostSummaryResponse> content = tradePostSlice.getContent().stream()
                .map(tradePostMapper::toDto)
                .toList();

        return SliceResponse.<TradePostSummaryResponse>builder()
                .content(content)
                .currentPage(tradePostSlice.getNumber())
                .pageSize(tradePostSlice.getNumberOfElements())
                .hasNextPage(tradePostSlice.hasNext())
                .build();
    }
}
