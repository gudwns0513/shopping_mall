package com.shop.shoppingmall.domain.tradehistory.controller;

import com.shop.shoppingmall.domain.tradehistory.service.TradeHistoryService;
import com.shop.shoppingmall.domain.tradepost.dto.TradePostSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade-histories")
public class TradeHistoryController {

    private final TradeHistoryService tradeHistoryService;


}
