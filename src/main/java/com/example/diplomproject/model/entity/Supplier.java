package com.example.diplomproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSupplier;
    @Column // Наименование организации
    private String organizationName;
    @Column // Юридический адрес
    private String legalAddress;
    @Column // Телефон
    private String phone;
    @Column // Почтовый индекс
    private String postalCode;
    @Column // Город
    private String city;
    @Column // Адрес
    private String address;
    @Column // Код банка (BIC)
    private String bankCode;
    @Column // Наименование банка
    private String bankName;
    @Column // УНН (Учетный номер налогоплательщика)
    private String taxId;
    @Column // ОКПО (Классификатор предприятий и организаций)
    private String registrationCode;
    @OneToOne
    private Account account;
}
