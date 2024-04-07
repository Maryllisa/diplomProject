package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeclarationTD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeclaration;
    @Column
    private String declarationNumber;
    @OneToOne
    private Supplier supplier;
    @Column
    private String customsCode;
    @Column
    private String formGr3;
    @Column
    private String specification;
    @Column
    private int colProducts;
    @Column // место
    private String allPlace;
    @Column
    private String declarationDetails;
    @Column
    private String recipientCompany;
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.Address")
    Address recipientAddress;
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.FinancialRegulator")
    private FinancialRegulator financialRegulator;
    // убрать 10 графу, она не заполняется
    @Column
    private String torgCountry;
    @Column
    private double cost;
    // графу 13 убрать

    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.Address")
    Address declarant;

    @Column
    private String codeSenderCountry;
    @Column
    private String nameSenderCountry;

    @Column
    private String codeOriginCountry;
    @Column
    private String nameOriginCountry;

    @Column
    private String codeRecipientCountry;
    @Column
    private String nameRecipientCountry;

    @Column
    private String identification;
    @Column
    private String vehicleRegistrationCountry;

    @Column
    private String codeContiner;







}
