package com.shop.shoppingmall.domain.chat.repository;

import com.shop.shoppingmall.domain.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
