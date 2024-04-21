package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {
    Optional<ChatRoom> findBySenderAndRecipient(Account sender, Account recipient);
    List<ChatRoom> findAllByIdChatRoomLike(String idChatRoom);
    List<ChatRoom> findBySenderOrRecipient(Account sender, Account recipient);

}
