package com.example.diplomproject.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private String title;
    private String  text;
    private Date dateSender;

}
