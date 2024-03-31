package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ChatMessage;
import com.example.diplomproject.model.entity.ChatRoom;
import com.example.diplomproject.model.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    boolean existsBySenderIdAndStatus(Account senderId, MessageStatus status);

    List<ChatMessage> findBySenderId(Account senderId);
    List<ChatMessage> findByChatRoomAndSenderId(ChatRoom chatRoom, Account senderId);
    List<ChatMessage> findAllBySenderIdAndStatus(Account senderId, MessageStatus status);

    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
