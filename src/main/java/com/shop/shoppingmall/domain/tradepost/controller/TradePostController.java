package com.shop.shoppingmall.domain.tradepost.controller;

import com.shop.shoppingmall.domain.tradepost.service.TradePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradePostController {

    private final TradePostService tradePostService;
}
