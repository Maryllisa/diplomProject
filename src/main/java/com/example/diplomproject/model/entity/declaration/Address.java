package com.example.diplomproject.model.entity.declaration;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
public class Address {
    private String city;
    private String postalCode;
    private String region;
    private String settlement;
    private String build;
    private String ogrnNumber;

}
