package com.example.diplomproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom {
    @Id
    private String idChatRoom;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account sender;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account recipient;
    @OneToMany
    private List<ChatMessage> chatMessageList;

}
