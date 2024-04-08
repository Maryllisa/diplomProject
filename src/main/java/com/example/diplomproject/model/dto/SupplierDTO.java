package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    private String organizationName;
    private String legalAddress;
    private String phone;
    private AddressDTO address;
    private String bankCode;
    private String bankName;
    //id="INN/KPP"
    private String taxId;
    //id="OGRN"
    private String registrationCode;
}
