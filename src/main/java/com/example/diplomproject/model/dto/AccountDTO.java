package com.example.diplomproject.model.dto;

import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;
import com.example.diplomproject.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends UserDTO {

    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    @NotEmpty
    private String surname;
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String name;
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String patronymic;
    @NotNull
    @ValidDateOfBirthRange
    private Date dateBirthday;
    @NotEmpty
    @Pattern(regexp = "^(375|80)(29|33|25)\\d{7}$")
    private String phone;


}
