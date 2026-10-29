package com.mazhar.chat_application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mazhar.chat_application.dto.request.ChatMessageRequest;
import com.mazhar.chat_application.dto.response.ChatMessageResponse;
import com.mazhar.chat_application.entity.ChatMessage;
import com.mazhar.chat_application.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageResponse saveMessage(String senderUsername, ChatMessageRequest request) {
        ChatMessage message = ChatMessage.builder()
                .senderUsername(senderUsername)
                .recipientUsername(request.getRecipientUsername())
                .content(request.getContent())
                .build();

        ChatMessage saved = chatMessageRepository.save(message);

        return mapToResponse(saved);
    }

    public List<ChatMessageResponse> getChatHistory(String user1, String user2) {
        return chatMessageRepository.findChatHistory(user1, user2)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ChatMessageResponse mapToResponse(ChatMessage msg) {
        return ChatMessageResponse.builder()
                .id(msg.getId())
                .senderUsername(msg.getSenderUsername())
                .recipientUsername(msg.getRecipientUsername())
                .content(msg.getContent())
                .timestamp(msg.getTimestamp())
                .build();
    }
}