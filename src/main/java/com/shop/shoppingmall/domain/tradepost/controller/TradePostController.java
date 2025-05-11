package com.shop.shoppingmall.domain.tradepost.controller;

import com.shop.shoppingmall.domain.tradepost.dto.TradePostDetailResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.global.response.SliceResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade-posts")
public class TradePostController {

    private final TradePostService tradePostService;

    //거래 게시물 등록
    @PostMapping
    public ResponseEntity<Void> registerTradePost(
            @Valid @RequestBody TradePostRegisterRequest tradePostRegisterRequest,
            @AuthenticationPrincipal OAuth2User oAuth2User) {

        Long userId = (Long)oAuth2User.getAttributes().get("id");
        tradePostService.registerTradePost(tradePostRegisterRequest, userId);
        return ResponseEntity.ok().build();
    }

    //거래 게시물 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTradePost(@PathVariable Long id,@Valid @RequestBody TradePostUpdateRequest tradePostUpdateRequest) {
        tradePostService.updateTradePost(id, tradePostUpdateRequest);
        return ResponseEntity.ok().build();
    }

    //거래 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTradePost(@PathVariable Long id) {
        tradePostService.deleteTradePost(id);
        return ResponseEntity.ok().build();
    }

    //거래 게시물 목록 조회
    @GetMapping
    public ResponseEntity<SliceResponse<TradePostSummaryResponse>> getTradePostList(@RequestParam(value = "category-id") Long categoryId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        SliceResponse<TradePostSummaryResponse> tradePostList = tradePostService.getTradePostList(categoryId, pageable);
        return ResponseEntity.ok(tradePostList);
    }

    //거래 게시물 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<TradePostDetailResponse> getTradePostDetail(@PathVariable Long id) {
        TradePostDetailResponse tradePostDetail = tradePostService.getTradePostDetail(id);
        return ResponseEntity.ok(tradePostDetail);
    }
}
