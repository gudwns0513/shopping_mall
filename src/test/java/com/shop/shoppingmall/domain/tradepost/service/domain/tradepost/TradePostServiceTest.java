package com.shop.shoppingmall.domain.tradepost.service.domain.tradepost;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.map.TradePostMapper;
import com.shop.shoppingmall.domain.tradepost.repository.TradePostRepository;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TradePostServiceTest {

    @Mock
    private TradePostRepository tradePostRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TradePostMapper tradePostMapper;

    @InjectMocks
    private TradePostService tradePostService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("가전 제품")
                .build();
    }

    @Test
    @DisplayName("거래 게시물 등록 - 정상")
    void registerTradePost() {
        // given
        TradePostRegisterRequest requestDto = TradePostRegisterRequest.builder()
                .title("제목")
                .description("설명")
                .price(10000)
                .categoryId(1L)
                .build();

        TradePost tradePost = TradePost.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();

        given(categoryRepository.findById(requestDto.getCategoryId()))
                .willReturn(Optional.of(category));
        given(tradePostMapper.toEntity(requestDto, category))
                .willReturn(tradePost);

        // when
        tradePostService.registerTradePost(requestDto);

        // then
        then(tradePostRepository).should(times(1)).save(tradePost);
    }

    @Test
    @DisplayName("거래 게시물 등록 - 존재하지 않는 카테고리")
    void registerTradePostNotExistCategory() {

        // given
        TradePostRegisterRequest requestDto = TradePostRegisterRequest.builder()
                .title("제목")
                .description("설명")
                .price(1000)
                .categoryId(99L)  // 존재하지 않는 카테고리 ID
                .build();

        given(categoryRepository.findById(requestDto.getCategoryId()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> tradePostService.registerTradePost(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 카테고리가 DB에 존재하지 않습니다.");
    }

    @Test
    @DisplayName("거래 게시물 수정 - 정상")
    void updateTradePost() {

        // given
        Long tradePostId = 1L;
        TradePost existingTradePost = TradePost.builder()
                .id(tradePostId)
                .title("이전 제목")
                .description("이전 설명")
                .price(500)
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();

        TradePostUpdateRequest updateRequest = TradePostUpdateRequest.builder()
                .title("새로운 제목")
                .description("새로운 설명")
                .price(1200)
                .categoryId(1L)
                .build();

        Category updatedCategory = Category.builder()
                .id(2L)
                .name("식품")
                .build();

        given(tradePostRepository.findById(tradePostId)).willReturn(Optional.of(existingTradePost));
        given(categoryRepository.findById(updateRequest.getCategoryId())).willReturn(Optional.of(updatedCategory));

        // when
        tradePostService.updateTradePost(tradePostId, updateRequest);

        // then
        assertThat(existingTradePost.getTitle()).isEqualTo("새로운 제목");
        assertThat(existingTradePost.getDescription()).isEqualTo("새로운 설명");
        assertThat(existingTradePost.getPrice()).isEqualTo(1200);
        assertThat(existingTradePost.getCategory()).isEqualTo(updatedCategory);
    }

    @Test
    @DisplayName("거래 게시물 삭제 - 정상")
    void deleteTradePost() {

        // given
        Long tradePostId = 1L;
        TradePost tradePost = TradePost.builder()
                .id(tradePostId)
                .title("Test Title")
                .description("Test Description")
                .price(500)
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();

        // Mocking
        given(tradePostRepository.findById(tradePostId)).willReturn(Optional.of(tradePost));

        // when
        tradePostService.deleteTradePost(tradePostId);

        // then
        then(tradePostRepository).should(times(1)).delete(tradePost);
    }
}
