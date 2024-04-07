package com.example.diplomproject.model.entity.declaration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FinancialRegulator {
    private String innKpp;
    private String organizationName;
    private String country;
    private String postalCode;
    private String region;
    private String locality;
    private String street;
    private String ogrn;
}
