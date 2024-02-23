package com.example.diplomproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends UserDTO {
    private String surname;
    private String name;
    private String patronymic;
    private Date dateBirthday;
    private String phone;
    private String position;

}
