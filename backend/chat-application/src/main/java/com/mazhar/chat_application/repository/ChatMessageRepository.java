package com.mazhar.chat_application.repository;

import com.mazhar.chat_application.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Do users ke beech ki chat history fetch karne ke liye (chronological order)
    @Query("SELECT m FROM ChatMessage m WHERE " +
           "(m.senderUsername = :user1 AND m.recipientUsername = :user2) OR " +
           "(m.senderUsername = :user2 AND m.recipientUsername = :user1) " +
           "ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(@Param("user1") String user1, @Param("user2") String user2);
}