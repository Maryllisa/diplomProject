package com.example.diplomproject.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPersonDTO {
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private String phone;
}
