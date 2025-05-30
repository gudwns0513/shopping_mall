package com.shop.shoppingmall.domain.chat.repository;

import com.shop.shoppingmall.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByTradePostIdAndBuyerId(Long tradePostId, Long buyerId);
}
