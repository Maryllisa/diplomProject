package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.chat.ChatMessage;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import com.example.diplomproject.model.entity.enumStatus.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    boolean existsBySenderIdAndStatus(Account senderId, MessageStatus status);

    List<ChatMessage> findBySenderId(Account senderId);
    List<ChatMessage> findByChatRoomAndSenderId(ChatRoom chatRoom, Account senderId);
    List<ChatMessage> findAllBySenderIdAndStatus(Account senderId, MessageStatus status);

    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);
}
