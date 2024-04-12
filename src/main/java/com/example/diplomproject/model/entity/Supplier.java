package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.SupplierDTO;
import com.example.diplomproject.model.entity.declaration.Address;
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
    @Column // Код банка (BIC)
    private String bankCode;
    @Column // Наименование банка
    private String bankName;
    @OneToOne
    private Address address;

    public Supplier(String organizationName, String legalAddress, String phone, Address address, String bankCode, String bankName, String taxId, String registrationCode) {
        this.organizationName = organizationName;
        this.legalAddress = legalAddress;
        this.phone = phone;
        this.address = address;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.taxId = taxId;
        this.registrationCode = registrationCode;
    }

    @Column // УНН (Учетный номер налогоплательщика)
    private String taxId;
    @Column // ОКПО (Классификатор предприятий и организаций)
    private String registrationCode;
    @OneToOne
    private ApplicationForStorage applicationForStorage;
    @OneToOne
    private Account account;

    public SupplierDTO buildDTO() {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setOrganizationName(organizationName);
        supplierDTO.setAddress(address);
        supplierDTO.setPhone(phone);
        supplierDTO.setBankCode(bankCode);
        supplierDTO.setLegalAddress(legalAddress);
        supplierDTO.setRegistrationCode(registrationCode);
        supplierDTO.setTaxId(taxId);
        supplierDTO.setBankName(bankName);
        return supplierDTO;
    }
}
