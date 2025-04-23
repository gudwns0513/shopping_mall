package com.shop.shoppingmall.domain.tradepost.service.domain.tradepost;

import com.shop.shoppingmall.domain.category.domain.Category;
import com.shop.shoppingmall.domain.category.repository.CategoryRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.domain.TradePostStatus;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostDetailResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.map.TradePostMapper;
import com.shop.shoppingmall.domain.tradepost.repository.TradePostRepository;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import com.shop.shoppingmall.global.exception.custom.CategoryNotFoundException;
import com.shop.shoppingmall.global.response.SliceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
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
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessage("해당 Category가 존재하지 않습니다. (categoryId: " + 99 + ")");
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
                .title("제목")
                .description("설명")
                .price(500)
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();

        given(tradePostRepository.findById(tradePostId)).willReturn(Optional.of(tradePost));

        // when
        tradePostService.deleteTradePost(tradePostId);

        // then
        then(tradePostRepository).should(times(1)).delete(tradePost);
    }

    @Test
    @DisplayName("거래 게시물 목록 조회 - 정상")
    void getTradePostList() {
        // given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        TradePost tradePost = TradePost.builder()
                .id(1L)
                .title("제목")
                .description("설명")
                .price(10000)
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();

        // TradePost가 포함된 Slice 객체를 반환하도록 설정
        Slice<TradePost> tradePostSlice = new SliceImpl<>(List.of(tradePost), pageable, false);

        given(tradePostRepository.findTradePostByCategoryId(category.getId(), pageable)).willReturn(tradePostSlice);
        given(tradePostMapper.toSummaryResponse(tradePost)).willReturn(
                TradePostSummaryResponse.builder()
                        .tradePostId(tradePost.getId())
                        .title(tradePost.getTitle())
                        .price(tradePost.getPrice())
                        .status(tradePost.getStatus())
                        .createdAt(tradePost.getCreatedAt())
                        .build()
        );

        // when
        SliceResponse<TradePostSummaryResponse> response = tradePostService.getTradePostList(category.getId(), pageable);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(1); // 반환된 게시물 개수 확인
        assertThat(response.getContent().get(0).getTradePostId()).isEqualTo(tradePost.getId()); // 게시물 ID 확인
        assertThat(response.getContent().get(0).getTitle()).isEqualTo(tradePost.getTitle()); // 제목 확인
        assertThat(response.getContent().get(0).getPrice()).isEqualTo(tradePost.getPrice()); // 가격 확인
        assertThat(response.getContent().get(0).getStatus()).isEqualTo(tradePost.getStatus()); // 상태 확인
        assertThat(response.getContent().get(0).getCreatedAt()).isEqualTo(tradePost.getCreatedAt()); // 생성일 확인
    }

    @Test
    @DisplayName("거래 게시물 상세 조회 - 정상")
    void getTradePostDetail() {

        //given
        TradePost tradePost = TradePost.builder()
                .id(1L)
                .title("제목")
                .description("설명")
                .price(10000)
                .status(TradePostStatus.AVAILABLE)
                .category(category)
                .build();

        given(tradePostRepository.findById(tradePost.getId()))
                .willReturn(Optional.of(tradePost));
        given(tradePostMapper.toDetailResponse(tradePost)).willReturn(
                TradePostDetailResponse.builder()
                        .tradePostId(tradePost.getId())
                        .title(tradePost.getTitle())
                        .description(tradePost.getDescription())
                        .price(tradePost.getPrice())
                        .status(tradePost.getStatus())
                        .createdAt(tradePost.getCreatedAt())
                        .updatedAt(tradePost.getUpdatedAt())
                        .build()
        );

        //when
        TradePostDetailResponse response = tradePostService.getTradePostDetail(tradePost.getId());

        //then
        assertThat(response).isNotNull();
        assertThat(response.getTradePostId()).isEqualTo(tradePost.getId());
        assertThat(response.getTitle()).isEqualTo(tradePost.getTitle());
        assertThat(response.getDescription()).isEqualTo(tradePost.getDescription());
        assertThat(response.getPrice()).isEqualTo(tradePost.getPrice());
        assertThat(response.getStatus()).isEqualTo(tradePost.getStatus());
        assertThat(response.getCreatedAt()).isEqualTo(tradePost.getCreatedAt());
        assertThat(response.getUpdatedAt()).isEqualTo(tradePost.getUpdatedAt());
    }
}
