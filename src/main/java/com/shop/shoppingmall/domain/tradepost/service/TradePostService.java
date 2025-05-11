package com.shop.shoppingmall.domain.tradepost.service;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import com.shop.shoppingmall.domain.tradehistory.service.TradeHistoryService;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostDetailResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.map.TradePostMapper;
import com.shop.shoppingmall.domain.tradepost.repository.TradePostRepository;
import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.domain.user.repository.UserRepository;
import com.shop.shoppingmall.global.exception.custom.CategoryNotFoundException;
import com.shop.shoppingmall.global.exception.custom.TradePostNotFoundException;
import com.shop.shoppingmall.global.exception.custom.UserNotFoundException;
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

    private final UserRepository userRepository;

    private final TradePostMapper tradePostMapper;

    private final TradeHistoryService tradeHistoryService;

    public void registerTradePost(TradePostRegisterRequest requestDto, Long userId) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(requestDto.getCategoryId()));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        TradePost tradePost = tradePostMapper.toEntity(requestDto, category, user);
        tradePostRepository.save(tradePost);
    }

    public void updateTradePost(Long tradePostId, TradePostUpdateRequest requestDto) {
        TradePost tradePost = tradePostRepository.findById(tradePostId)
                .orElseThrow(() -> new TradePostNotFoundException(tradePostId));

        Category category = null;
        if(requestDto.getCategoryId() != null) {
            category = categoryRepository.findById(requestDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(requestDto.getCategoryId()));
        }

        tradePost.updateTradePost(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getStatus(),
                category
        );

        //거래 완료 시 TradeHistory 저장
        if(tradePost.getStatus() == TradePostStatus.COMPLETE) {
            tradeHistoryService.recordTradeCompletion(tradePost);
        }
    }

    public void deleteTradePost(Long tradePostId) {
        TradePost tradePost = tradePostRepository.findById(tradePostId)
                .orElseThrow(() -> new TradePostNotFoundException(tradePostId));
        tradePost.delete(); //논리 삭제
    }

    public SliceResponse<TradePostSummaryResponse> getTradePostList(Long categoryId, Pageable pageable) {
        Slice<TradePost> tradePostSlice = tradePostRepository.findByCategoryIdAndIsDeletedFalse(categoryId, pageable);

        List<TradePostSummaryResponse> content = tradePostSlice.getContent().stream()
                .map(tradePostMapper::toSummaryResponse)
                .toList();

        return SliceResponse.<TradePostSummaryResponse>builder()
                .content(content)
                .currentPage(tradePostSlice.getNumber())
                .pageSize(tradePostSlice.getNumberOfElements())
                .hasNextPage(tradePostSlice.hasNext())
                .build();
    }

    public TradePostDetailResponse getTradePostDetail(Long tradePostId) {
        TradePost tradePost = tradePostRepository.findById(tradePostId)
                .orElseThrow(() -> new TradePostNotFoundException(tradePostId));

        //삭제된 TradePost 예외 처리
        if(tradePost.isDeleted()) {
            throw new TradePostNotFoundException(tradePostId);
        }

        return tradePostMapper.toDetailResponse(tradePost);
    }
}
