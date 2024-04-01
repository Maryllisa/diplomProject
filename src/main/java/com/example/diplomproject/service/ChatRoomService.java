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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository messageRepository;
    public ChatRoomDTO findChatMessages(String senderLogin, String recipientLogin) {
        Account sender = userRepository.findByLogin(senderLogin);
        Account recipient = userRepository.findByLogin(recipientLogin);
        if (sender==null && recipient==null) return null;
        String chatId = senderLogin + " " + recipientLogin;
        ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElse(null);
        if (chatRoom==null){
            chatId = recipientLogin + " " + senderLogin;
            chatRoom = chatRoomRepository.findById(chatId).orElse(null);
            if (chatRoom==null) {
                chatRoom = new ChatRoom(chatId,
                        sender,
                        recipient,
                        null);
                chatRoom = chatRoomRepository.save(chatRoom);
            }
        }
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setIdChatRoom(chatRoom.getIdChatRoom());
        chatRoomDTO.setLoginUserSender(chatRoom.getSender().getLogin());
        chatRoomDTO.setLoginUserRecipient(chatRoom.getRecipient().getLogin());
        if (chatRoom.getChatMessageList()!=null) {
            List<ChatMessage> messageList = messageRepository.findByChatRoom(chatRoom);
            for (ChatMessage msg : messageList) {
                chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                        chatRoomDTO.getLoginUserSender(),
                        msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                        msg.getContent(), msg.getTimestamp(),
                        msg.getStatus().toString()));
                msg.setStatus(MessageStatus.RECEIVED);
                messageRepository.save(msg);
            }
        }
        return chatRoomDTO;
    }

    public List<ChatRoomDTO> findChatMessagesSender(String senderLogin) {
        Account sender = userRepository.findByLogin(senderLogin);
        if (sender==null ) return null;
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByIdChatRoomLike(senderLogin);
        if (chatRoomList==null){
                return null;
        }
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();
        for(ChatRoom chatRoom :chatRoomList){
            ChatRoomDTO chatRoomDTO =new ChatRoomDTO();
            chatRoomDTO.setIdChatRoom(chatRoom.getSender().getLogin() + " " + chatRoom.getRecipient().getLogin());
            chatRoomDTO.setLoginUserSender(chatRoom.getSender().getLogin());
            chatRoomDTO.setLoginUserRecipient(chatRoom.getRecipient().getLogin());
            List<ChatMessage> messageList = messageRepository.findByChatRoom(chatRoom);
            for (ChatMessage msg : messageList) {
                chatRoomDTO.setMessageDTOList(new MessageDTO(chatRoom.getIdChatRoom(),
                        chatRoomDTO.getLoginUserSender(),
                        msg.getSenderId().getSurname() + " " + msg.getSenderId().getName(),
                        msg.getContent(), msg.getTimestamp(),
                        msg.getStatus().toString()));
            }
            chatRoomDTOList.add(chatRoomDTO);
        }
        return chatRoomDTOList;
    }
}
