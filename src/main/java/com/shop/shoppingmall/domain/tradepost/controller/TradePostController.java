package com.shop.shoppingmall.domain.tradepost.controller;

import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import com.shop.shoppingmall.global.response.SliceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade-posts")
public class TradePostController {

    private final TradePostService tradePostService;

    //거래 게시물 등록
    @PostMapping
    public ResponseEntity<Void> registerTradePost(@Valid @RequestBody TradePostRegisterRequest tradePostRegisterRequest) {
        tradePostService.registerTradePost(tradePostRegisterRequest);
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
}
