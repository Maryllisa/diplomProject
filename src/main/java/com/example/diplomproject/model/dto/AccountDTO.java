package com.example.diplomproject.model.dto;

import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;
import com.example.diplomproject.model.entity.User;
import lombok.*;


import javax.validation.constraints.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO extends UserDTO {

    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    @NotEmpty@NotNull
    private String surname;
    @NotEmpty@NotNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String name;
    @NotEmpty@NotNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String patronymic;
    @NotNull
    @ValidDateOfBirthRange
    private Date dateBirthday;
    @NotEmpty@NotNull
    @Pattern(regexp = "^\\+(375|80)(29|33|25)\\d{7}$")
    private String phone;


}
