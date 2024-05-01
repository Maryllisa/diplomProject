package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndividualsDTO {
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String organizationName;
    @NotEmpty
    private String legalAddress;
    @NotEmpty
    @Pattern(regexp = "^\\+(375|80)(29|33|25)\\d{7}$")
    private String phone;
    private AddressDTO address;
    private String bankCode;
    private String bankName;
    //id="INN/KPP"
    @NotEmpty
    @Pattern(regexp = "\\d{10,12}\\|\\d{9}")
    private String taxId;
    //id="OGRN"
    @NotEmpty
    @Pattern(regexp = "[АВЕКМНОРСТУХABEKMHOPCTYX]\\d{3}[АВЕКМНОРСТУХABEKMHOPCTYX]{2}\\d{2,3}")
    private String registrationCode;
    private RoleIndividuals roleIndividuals;

    public Individuals build(RoleIndividuals roleIndividuals) {
        return Individuals.builder()
                .organizationName(organizationName)
                .legalAddress(legalAddress)
                .roleIndividuals(roleIndividuals)
                .phone(phone)
                .bankCode(bankCode)
                .bankName(bankName)
                .taxId(taxId)
                .registrationCode(registrationCode)
                .address(address.build())
                .build();
    }
}
