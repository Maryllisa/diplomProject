package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DeclarationTD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeclaration;
    /*
                Декларация
     */
    @Column
    private String declarationNumber;
    /*
            Отправитель
    */
    @JsonFormat
    @OneToOne
    private Individuals individuals;
    /*
            Формы
    */
    @Column
    private String formGr3;
    /*
            спецификация
    */
    @Column
    private String specification;
    /*
            всего товаров
    */
    @Column
    private int colProducts;
    /*
            Всего мест
    */
    @Column
    private String allPlace;
    /*
        Особенности декларирования
    */
    @Column
    private String declarationDetails;
    /*
        Получатель
    */
    @JsonFormat
    @OneToOne
    private Individuals recipientAddress;
    /*
        Лицо ответственное за финансовое регулирование
    */
    @OneToOne
    private Individuals financialRegulator;
    // убрать 10 графу, она не заполняется
    /*
            Торгующая страна
    */
    @Column
    private String torgCountry;
    /*
           Общая томоженная стоимость
    */
    @Column
    private double cost;
    // графу 13 убрать
    /*
            декларант
    */
    @JsonFormat
    @OneToOne
    private Individuals declarant;
    /*
            Страна отправления
            Код
            Название страны
    */
    @Column
    private String codeSenderCountry;
    @Column
    private String nameSenderCountry;
    /*
            Страна происхождения
            Код
            Название страны
    */
    @Column
    private String codeOriginCountry;
    @Column
    private String nameOriginCountry;
    /*
           Страна назначения
            Код
            Название страны
    */
    @Column
    private String codeRecipientCountry;
    @Column
    private String nameRecipientCountry;
    /*
            Идентификация и страна регистрации транспортного средства
            кол-во тс.
            Номера т.с.
    */
    @Column
    private String identification;
    @Column
    private String vehicleRegistrationCountry;
    /*
               Контейнер
    */
    @Column
    private String codeContiner;
    /*
            Условия поставки
            код у.п.
            Наименование условий поставки
    */
    @Column
    private String upCode;
    @Column
    private String conditionsOfDeliveryName;
    /*
           Идентификация и страна регистрации активного транспортного средства на границе
           кол-во тс.
           Номера транспортного средства
    */
    @Column
    private String numberOfVehicles;
    @Column
    private String vehicleNumbers;
    /*
           Валюта и общая сумма по счету
           Код валюты
           Сумма по счету
   */
    @Column
    private String currency;
    @Column
    private String accountTotalAmount;
    /*
           Курс валюты
    */
    @JsonFormat
    @OneToOne
    private CurrencyRate currencyRate;
    /*
           Характер сделки
           Код сделки
           Код особ внешнеэкономич сделки
    */
    @Column
    private String dealCode;
    @Column
    private String specialEconomicDealCode;
    /*
           Вид транспорта на границе
    */
    @Column
    private String codeTransport;
    /*
           Вид транспорта внутри страны
    */
    @Column
    private String codeTransportInCountry;
    /*
           Суммарный вес брутто
    */
    @Column
    private double totalGrossWeight;
    /*
           Суммарный вес нетто
    */
    @Column
    private double totalNetWeight;
    /*
           Таможня на границе
           Код таможни
           Описание
    */
    @Column
    private String customsBorderCode;
    @Column
    private String customsBorderDescription;
    /*
           Таможня на границе
           Код таможни
           Описание
    */
    @OneToOne
    private ProductLocation productLocation;
    /*
           Грузовые места и описание товаров
    */
    @Column
    private String productDescription;
    @JsonFormat
    @ManyToOne
    private Account account;
    @JsonFormat
    @OneToMany
    private List<Product> productList;

    public DeclarationDTO build(RoleIndividuals roleIndividuals){
        String declarationNumberArray [] = declarationNumber.split("/");
        String customEDCode = declarationNumberArray[0];
        String directionOfMovement = declarationNumberArray[1];
        String procedureCode = declarationNumberArray[2];

        String formGr3Array [] = formGr3.split("/");
        String numbList = formGr3Array[0];
        String colList = formGr3Array[1];

        String specificationArray [] = specification.split("/");
        String colSpec = specificationArray[0];
        String colListSpec = specificationArray[1];

        return DeclarationDTO.builder()
                .idDeclaration(idDeclaration)
                .customEDCode(customEDCode)
                .directionOfMovement(directionOfMovement)
                .procedureCode(procedureCode)
                .numbList(numbList)
                .colList(colList)
                .colSpec(colSpec)
                .colListSpec(colListSpec)
                .senderDTO(individuals.build(RoleIndividuals.SUPPLIER))
                .colProd(colProducts)
                .osobenOfDeclar(declarationDetails)
                .recipientDTO(recipientAddress.build(RoleIndividuals.RECIPIENT))
                .otvetstvenoeFace(financialRegulator.build(RoleIndividuals.FINYREG))
                .tradingCountry(torgCountry)
                .tamPrice(cost)
                .declarator(declarant.build(RoleIndividuals.DECLARANT))
                .countryCodeDeparture(codeSenderCountry)
                .countryNameDeparture(nameSenderCountry)
                .countryCodeOrigin(codeOriginCountry)
                .countryNameOrigin(nameOriginCountry)
                .countryCodeDestination(codeRecipientCountry)
                .countryNameDestination(nameRecipientCountry)
                .vehicleCount(identification)
                .vehicleNumbers(vehicleRegistrationCountry)
                .conteiner(codeContiner)
                .codeYP(upCode)
                .nameYP(conditionsOfDeliveryName)
                .vehicleCountOnBorder(numberOfVehicles)
                .vehicleNumbersOnBorder(vehicleNumbers)
                .currencyCode(currency)
                .invoiceAmount(accountTotalAmount)
                .currencyRateDTO(currencyRate.build())
                .transactionCharacterCode(dealCode)
                .foreignTradeFeatureCode(specialEconomicDealCode)
                .borderTransportTypeCode(codeTransport)
                .domesticTransportTypeCode(codeTransportInCountry)
                .grossWeight(totalGrossWeight)
                .netWeight(totalNetWeight)
                .customsCode(customsBorderCode)
                .largeTextArea(customsBorderDescription)
                .productLocationDTO(productLocation.build())
                .productDescription(productDescription)
                .build();
    }
    public DeclarationDTO build(){
        String declarationNumberArray [] = declarationNumber.split("/");
        String customEDCode = declarationNumberArray[0];
        String directionOfMovement = declarationNumberArray[1];
        String procedureCode = declarationNumberArray[2];

        String formGr3Array [] = formGr3.split("/");
        String numbList = formGr3Array[0];
        String colList = formGr3Array[1];

        String specificationArray [] = specification.split("/");
        String colSpec = specificationArray[0];
        String colListSpec = specificationArray[1];

        return DeclarationDTO.builder()
                .idDeclaration(idDeclaration)
                .customEDCode(customEDCode)
                .directionOfMovement(directionOfMovement)
                .procedureCode(procedureCode)
                .numbList(numbList)
                .colList(colList)
                .colSpec(colSpec)
                .colListSpec(colListSpec)
                .senderDTO(individuals.build(individuals.getRoleIndividuals()))
                .colProd(colProducts)
                .osobenOfDeclar(declarationDetails)
                .recipientDTO(recipientAddress.build(recipientAddress.getRoleIndividuals()))
                .otvetstvenoeFace(financialRegulator.build(financialRegulator.getRoleIndividuals()))
                .tradingCountry(torgCountry)
                .tamPrice(cost)
                .declarator(declarant.build(declarant.getRoleIndividuals()))
                .countryCodeDeparture(codeSenderCountry)
                .countryNameDeparture(nameSenderCountry)
                .countryCodeOrigin(codeOriginCountry)
                .countryNameOrigin(nameOriginCountry)
                .countryCodeDestination(codeRecipientCountry)
                .countryNameDestination(nameRecipientCountry)
                .vehicleCount(identification)
                .vehicleNumbers(vehicleRegistrationCountry)
                .conteiner(codeContiner)
                .codeYP(upCode)
                .nameYP(conditionsOfDeliveryName)
                .vehicleCountOnBorder(numberOfVehicles)
                .vehicleNumbersOnBorder(vehicleNumbers)
                .currencyCode(currency)
                .invoiceAmount(accountTotalAmount)
                .currencyRateDTO(currencyRate.build())
                .transactionCharacterCode(dealCode)
                .foreignTradeFeatureCode(specialEconomicDealCode)
                .borderTransportTypeCode(codeTransport)
                .domesticTransportTypeCode(codeTransportInCountry)
                .grossWeight(totalGrossWeight)
                .netWeight(totalNetWeight)
                .customsCode(customsBorderCode)
                .largeTextArea(customsBorderDescription)
                .productLocationDTO(productLocation.build())
                .productDescription(productDescription)
                .build();
    }

}
