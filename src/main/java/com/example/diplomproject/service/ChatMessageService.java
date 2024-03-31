package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.message.ChatRoomDTO;
import com.example.diplomproject.model.dto.message.MessageDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ChatMessage;
import com.example.diplomproject.model.entity.ChatRoom;
import com.example.diplomproject.model.entity.MessageStatus;
import com.example.diplomproject.repository.ChatMessageRepository;
import com.example.diplomproject.repository.ChatRoomRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.DELIVERED);
        return messageRepository.save(chatMessage);
    }

    public ChatMessage saveMessage(MessageDTO messageDTO) {
        ChatMessage message = new ChatMessage();
        message.setStatus(MessageStatus.DELIVERED);
        Account account = userRepository.findByLogin(messageDTO.getSenderLogin());
        Account accountRep = new Account();
        String[] words = messageDTO.getIdChatRoom().split(" ");
        for (String word : words) {
            if (!word.equals(account.getLogin())) {
                accountRep = userRepository.findByLogin(word);
            }
        }
        ChatRoom chatRoom = chatRoomRepository.findById(words[0] + " " + words[1]).orElse(null);
        if (chatRoom == null) {
            chatRoom = chatRoomRepository.findById(words[1] + " " + words[0]).orElse(null);
        }
        message.setChatRoom(chatRoom);
        message.setSenderId(account);
        message.setContent(messageDTO.getContent());
        message.setTimestamp(messageDTO.getTimestamp());
        return messageRepository.save(message);

    }
}
