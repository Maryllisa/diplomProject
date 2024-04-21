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
public class UserDTO {
    private String login;
    private String surname;
    private String name;
    private String patronymic;
    private String Role;
    private List<ChatRoomDTO> chatRoomDTOS = new ArrayList<>();
    public void setChatRoomDTOS(ChatRoomDTO chatRoomDTO) {
        this.chatRoomDTOS.add(chatRoomDTO);
    }
}
