package com.example.diplomproject.model.dto;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotEmpty@NotNull
    @Size(min = 4, max = 20)
    private String login;
    @NotEmpty@NotNull
    @Size(min = 4, max = 20)
    private String password;
    @NotEmpty@NotNull
    @Email
    private String email;
}
