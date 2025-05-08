package com.shop.shoppingmall.domain.chat.service;

import com.shop.shoppingmall.domain.chat.domain.ChatRoom;
import com.shop.shoppingmall.domain.chat.dto.ChatRoomDetailResponse;
import com.shop.shoppingmall.domain.chat.map.ChatRoomMapper;
import com.shop.shoppingmall.domain.chat.repository.ChatRoomRepository;
import com.shop.shoppingmall.domain.tradepost.domain.TradePost;
import com.shop.shoppingmall.domain.tradepost.repository.TradePostRepository;
import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.domain.user.repository.UserRepository;
import com.shop.shoppingmall.global.exception.custom.TradePostNotFoundException;
import com.shop.shoppingmall.global.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    private final TradePostRepository tradePostRepository;

    private final UserRepository userRepository;

    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomDetailResponse findOrCreateChatRoom(Long tradePostId, Long buyerId) {

        TradePost tradePost = tradePostRepository.findById(tradePostId)
                .orElseThrow(() -> new TradePostNotFoundException(tradePostId));

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new UserNotFoundException(buyerId));

        ChatRoom chatRoom = chatRoomRepository.findByTradePostIdAndBuyerId(tradePostId, buyerId)
                .orElseGet(() -> chatRoomRepository.save(
                        chatRoomMapper.toEntity(tradePost, buyer))
                );

        return chatRoomMapper.toDetailResponse(chatRoom);
    }
}
