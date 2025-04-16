package com.shop.shoppingmall.domain.tradepost.controller;

import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostRegisterRequest;
import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TradePostController {

    private final TradePostService tradePostService;

    @PostMapping("/trade-post")
    public ResponseEntity<Void> registerTradePost(@Valid @RequestBody TradePostRegisterRequest tradePostRegisterRequest) {
        tradePostService.registerTradePost(tradePostRegisterRequest);
        return ResponseEntity.noContent().build();
    }
}
