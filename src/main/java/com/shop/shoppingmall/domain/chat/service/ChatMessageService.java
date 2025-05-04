package com.shop.shoppingmall.domain.chat.service;

import com.shop.shoppingmall.domain.chat.domain.ChatMessage;
import com.shop.shoppingmall.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void saveMessage(ChatMessage chatMessage) {

    }
}
