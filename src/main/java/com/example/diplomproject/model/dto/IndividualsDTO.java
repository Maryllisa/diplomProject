package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualsDTO {
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
    private RoleIndividuals roleIndividuals;

    public Individuals build() {
        return Individuals.builder()
                .organizationName(organizationName)
                .legalAddress(legalAddress)
                .phone(phone)
                .bankCode(bankCode)
                .bankName(bankName)
                .address(address.build())
                .build();
    }
}
