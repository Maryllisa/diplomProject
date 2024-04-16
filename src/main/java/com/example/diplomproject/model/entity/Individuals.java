package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.entity.declaration.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonFormat
    @OneToOne
    private Account account;
    @Column // УНН (Учетный номер налогоплательщика)
    private String taxId;
    @Column // ОКПО (Классификатор предприятий и организаций)
    private String registrationCode;

    public IndividualsDTO build(RoleIndividuals roleIndividuals) {
        return IndividualsDTO.builder()
                .organizationName(organizationName)
                .legalAddress(legalAddress)
                .phone(phone)
                .bankCode(bankCode)
                .bankName(bankName)
                .address(address.build())
                .build();
    }
}
