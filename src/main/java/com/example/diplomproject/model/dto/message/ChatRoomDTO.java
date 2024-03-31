package com.example.diplomproject.model.dto.message;

import com.example.diplomproject.model.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private String idChatRoom;
    private String loginUserSender;
    private String loginUserRecipient;
    private List<MessageDTO> messageDTOList = new ArrayList<>();

    public void setMessageDTOList(MessageDTO messageDTO) {
        this.messageDTOList.add(messageDTO);
    }

}
