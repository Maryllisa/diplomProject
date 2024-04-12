package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.FinancialRegulator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialRegulatorDTO {
    private String innKpp;
    private String organizationName;
    private String country;
    private String postalCode;
    private String region;
    private String locality;
    private String street;
    private String ogrn;

    public FinancialRegulator build(){
        return new FinancialRegulator(innKpp, organizationName,country,postalCode,region,locality,street,ogrn);
    }

}
