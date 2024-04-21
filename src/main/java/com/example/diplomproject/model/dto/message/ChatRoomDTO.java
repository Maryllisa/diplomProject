package com.example.diplomproject.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomDTO {
    private String idChatRoom;
    private String loginUserSender;
    private String loginUserRecipient;
    private List<MessageDTO> messageDTOList = new ArrayList<>();

    public void setMessageDTOList(MessageDTO messageDTO) {
        this.messageDTOList.add(messageDTO);
    }

}
