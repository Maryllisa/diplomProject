package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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


    public Individuals build(){
        Individuals in = new Individuals();
        in.setOrganizationName(organizationName);
        in.setAddress(address.build());
        in.setPhone(phone);
        in.setBankCode(phone);
        in.setLegalAddress(phone);
        in.setRegistrationCode(registrationCode);
        in.setTaxId(taxId);
        in.setRoleIndividuals(roleIndividuals);
        in.setBankName(bankName);
        return in;
    }
}