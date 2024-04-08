package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.Supplier;
import com.example.diplomproject.model.entity.declaration.Address;
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

    public Supplier build(){
        Supplier supplier = new Supplier();
        supplier.setOrganizationName(organizationName);
        supplier.setAddress(address.build());
        supplier.setPhone(phone);
        supplier.setBankCode(phone);
        supplier.setLegalAddress(phone);
        supplier.setRegistrationCode(registrationCode);
        supplier.setTaxId(taxId);
        supplier.setBankName(bankName);
        return supplier;
    }
}
