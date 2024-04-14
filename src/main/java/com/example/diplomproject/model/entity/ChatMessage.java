package com.example.diplomproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private Account senderId;
    @Column
    private String content;
    @Column
    private Date timestamp;
    @Column
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

}
