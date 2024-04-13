package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.entity.declaration.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Individuals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSupplier;
    @Column // Наименование организации
    private String organizationName;
    @Column // Юридический адрес
    private String legalAddress;
    @Column // Телефон
    private String phone;
    @Column // Код банка (BIC)
    private String bankCode;
    @Column // Наименование банка
    private String bankName;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleIndividuals roleIndividuals;
    @OneToOne
    private Address address;
    @OneToOne
    private Account account;

    public Individuals(String organizationName, String legalAddress, String phone,
                       Address address, String bankCode, String bankName,
                       String taxId, String registrationCode, RoleIndividuals roleIndividuals) {
        this.organizationName = organizationName;
        this.legalAddress = legalAddress;
        this.phone = phone;
        this.address = address;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.roleIndividuals = roleIndividuals;
        this.taxId = taxId;
        this.registrationCode = registrationCode;
    }

    @Column // УНН (Учетный номер налогоплательщика)
    private String taxId;
    @Column // ОКПО (Классификатор предприятий и организаций)
    private String registrationCode;
    public IndividualsDTO buildDTO() {
        IndividualsDTO individualsDTO = new IndividualsDTO();
        individualsDTO.setOrganizationName(organizationName);
        individualsDTO.setAddress(address.build());
        individualsDTO.setPhone(phone);
        individualsDTO.setBankCode(bankCode);
        individualsDTO.setLegalAddress(legalAddress);
        individualsDTO.setRegistrationCode(registrationCode);
        individualsDTO.setTaxId(taxId);
        individualsDTO.setBankName(bankName);
        individualsDTO.setRoleIndividuals(roleIndividuals);
        return individualsDTO;
    }
}
