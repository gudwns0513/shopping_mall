package com.shop.shoppingmall.domain.chat.service;

import com.shop.shoppingmall.domain.chat.domain.ChatMessage;
import com.shop.shoppingmall.domain.chat.domain.ChatRoom;
import com.shop.shoppingmall.domain.chat.dto.ChatMessageSendRequest;
import com.shop.shoppingmall.domain.chat.dto.ChatMessageDetailResponse;
import com.shop.shoppingmall.domain.chat.map.ChatMessageMapper;
import com.shop.shoppingmall.domain.chat.repository.ChatMessageRepository;
import com.shop.shoppingmall.domain.chat.repository.ChatRoomRepository;
import com.shop.shoppingmall.domain.user.domain.User;
import com.shop.shoppingmall.domain.user.repository.UserRepository;
import com.shop.shoppingmall.global.exception.custom.ChatRoomNotFoundException;
import com.shop.shoppingmall.global.exception.custom.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final UserRepository userRepository;

    private final ChatMessageMapper chatMessageMapper;

    public ChatMessageDetailResponse saveMessage(ChatMessageSendRequest chatMessageRequest, Long senderId) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatMessageRequest.getChatRoomId())
                .orElseThrow(() -> new ChatRoomNotFoundException(chatMessageRequest.getChatRoomId()));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));

        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageRequest.getContent(), chatRoom, sender);
        chatMessageRepository.save(chatMessage);

        return chatMessageMapper.toDetailResponse(chatMessage);
    }
}
