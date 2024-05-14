package com.example.diplomproject.model.entity.chat;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.MarkForAgency;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
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
    @OneToOne
    private MarkForAgency markForAgency;

}
