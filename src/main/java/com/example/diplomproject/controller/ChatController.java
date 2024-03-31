package com.example.diplomproject.controller;

import com.example.diplomproject.model.dto.AccountDTO;
import com.example.diplomproject.model.dto.message.MessageDTO;
import com.example.diplomproject.model.dto.message.UserDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ChatMessage;
import com.example.diplomproject.service.AccountService;
import com.example.diplomproject.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final AccountService accountService;
    private final ChatMessageService chatMessageService;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/queue/messages")
    public MessageDTO sendMessage(@Payload MessageDTO MessageDTO) {
        ChatMessage chatMessage = chatMessageService.saveMessage(MessageDTO);
        return MessageDTO;
    }
    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserDTO addUser(
            @Payload Account user
    ) {
        return accountService.changeStatusOnline(user);

    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public Account disconnectUser(
            @Payload Account user
    ) {
        accountService.disconnect(user);
        return user;
    }




}
