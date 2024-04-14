package com.example.diplomproject.model.dto.message;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {

    private String idChatRoom;
    private String senderLogin;
    private String name;
    private String content;
    private Date timestamp;
    private String status;

}
