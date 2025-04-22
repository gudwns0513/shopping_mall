package com.shop.shoppingmall.domain.tradepost.controller;

import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostUpdateRequest;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TradePostController {

    private final TradePostService tradePostService;

    //거래 게시물 등록
    @PostMapping("/trade-post")
    public ResponseEntity<Void> registerTradePost(@Valid @RequestBody TradePostRegisterRequest tradePostRegisterRequest) {
        tradePostService.registerTradePost(tradePostRegisterRequest);
        return ResponseEntity.ok().build();
    }

    //거래 게시물 수정
    @PatchMapping("/trade-post/{id}")
    public ResponseEntity<Void> updateTradePost(@PathVariable Long id,@Valid @RequestBody TradePostUpdateRequest tradePostUpdateRequest) {
        tradePostService.updateTradePost(id, tradePostUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/trade-post/{id}")
    public ResponseEntity<Void> deleteTradePost(@PathVariable Long id) {
        tradePostService.deleteTradePost(id);
        return ResponseEntity.ok().build();
    }
}
