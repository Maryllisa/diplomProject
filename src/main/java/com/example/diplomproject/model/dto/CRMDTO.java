package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.CRM;
import com.example.diplomproject.model.entity.CustomsProcessing;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CRMDTO {

    private IndividualsDTO sender;
    private IndividualsDTO resipient;
    /*
    * * * Место разгрузки
    */
    private String countryWH;
    private String cityWH;
    private String streetWH;
    private String houseNumberWH;
    /*
     * * * Место и дата погрузки
     */
    private String loadingCountry;
    private String loadingCity;
    private String loadingStreet;
    private String loadingHouseNumber;
    private Date loadingDate;
    /*
     * * * Прилагаемые документы
     */
    private String invoiceDocument;
    private String shippingSpecificationDocument;
    private String qualityCertificateDocument;
    private String veterinaryCertificateDocument;
    private String quarantineCertificateDocument;
    private String certificateOfOriginDocument;
    private String loadingCertificateDocument;
    /*
     * * * 6-9
     */
    private String cargoQuantity;
    private String cargoName;
    private String nackagingType;
    private String numbers;
    /*
     * * * Статистический код
     */
    private String statistikCode;
    /*
     * * * Суммарный вес брутто
     */
    private String grossWeight;
    /*
     * * * Объем
     */
    private String volume;
    /*
     * * * Таможенная обработка
     */
    private CustomsProcessingDTO customsProcessing;
    /*
     * * * Возврат
     */
    private String returnCountry;
    private String returnCity;
    private String returnStreet;
    private String returnHouse;
    /*
     * * * Условия оплаты
    */
    private String paymentTerms;
    /*
     * * * Перевозчик
     */
    private IndividualsDTO carrier;
    /*
     * * * Последующий перевозчик
     */
    private IndividualsDTO subsequentCarrier;
    /*
     * * * Оговорки и замечания перевозчика
     */
    private String carrierNotes;
    /*
     * * * Дата получения груза
     */
    private String cargoReceivedDate;
    /*
     * * * Дата заполнения СМR
     */
    private Date cmrFilledDate;
    /*
     * * * Регистрационные номера
     */
    private String tractorRegistrationNumber;
    private String trailerRegistrationNumber;

    public CRM build() {
        return CRM.builder()
                .sender(Individuals.builder()
                        .organizationName(sender.getOrganizationName())
                        .legalAddress(sender.getLegalAddress())
                        .phone(sender.getPhone())
                        .bankCode(sender.getBankCode())
                        .bankName(sender.getBankName())
                        .roleIndividuals(RoleIndividuals.SUPPLIER)
                        .address(Address.builder()
                                .city(sender.getAddress().getCity())
                                .postalCode(sender.getAddress().getPostalCode())
                                .region(sender.getAddress().getRegion())
                                .settlement(sender.getAddress().getSettlement())
                                .build(sender.getAddress().getBuild())
                                .ogrnNumber(sender.getAddress().getOgrnNumber())
                                .build())
                        .build())
                .resipient(Individuals.builder()
                        .organizationName(resipient.getOrganizationName())
                        .legalAddress(resipient.getLegalAddress())
                        .phone(resipient.getPhone())
                        .bankCode(resipient.getBankCode())
                        .bankName(resipient.getBankName())
                        .roleIndividuals(RoleIndividuals.RECIPIENT)
                        .address(Address.builder()
                                .city(resipient.getAddress().getCity())
                                .postalCode(resipient.getAddress().getPostalCode())
                                .region(resipient.getAddress().getRegion())
                                .settlement(resipient.getAddress().getSettlement())
                                .build(resipient.getAddress().getBuild())
                                .ogrnNumber(resipient.getAddress().getOgrnNumber())
                                .build())
                        .build())
                .countryWH(countryWH)
                .cityWH(cityWH)
                .streetWH(streetWH)
                .houseNumberWH(houseNumberWH)
                .loadingCountry(loadingCountry)
                .loadingCity(loadingCity)
                .loadingStreet(loadingStreet)
                .loadingHouseNumber(loadingHouseNumber)
                .loadingDate(loadingDate)
                .invoiceDocument(invoiceDocument)
                .shippingSpecificationDocument(shippingSpecificationDocument)
                .qualityCertificateDocument(qualityCertificateDocument)
                .veterinaryCertificateDocument(veterinaryCertificateDocument)
                .certificateOfOriginDocument(certificateOfOriginDocument)
                .loadingCertificateDocument(loadingCertificateDocument)
                .cargoName(cargoQuantity)
                .cargoName(cargoName)
                .nackagingType(nackagingType)
                .numbers(numbers)
                .statistikCode(statistikCode)
                .grossWeight(grossWeight)
                .volume(volume)
                .customsProcessing(CustomsProcessing.builder()
                        .customsPostName(customsProcessing.getCustomsPostName())
                        .customsCode(customsProcessing.getCustomsCode())
                        .svhNameAndAddress(customsProcessing.getSvhNameAndAddress())
                        .licenseNumber(customsProcessing.getLicenseNumber())
                        .issueDate(customsProcessing.getIssueDate())
                        .build())
                .returnCountry(returnCountry)
                .returnCity(returnCity)
                .returnStreet(returnStreet)
                .returnHouse(returnHouse)
                .paymentTerms(paymentTerms)
                .carrier((Individuals.builder()
                        .organizationName(carrier.getOrganizationName())
                        .legalAddress(carrier.getLegalAddress())
                        .phone(carrier.getPhone())
                        .bankCode(carrier.getBankCode())
                        .bankName(carrier.getBankName())
                        .roleIndividuals(RoleIndividuals.CARRIER)
                        .address(Address.builder()
                                .city(carrier.getAddress().getCity())
                                .postalCode(carrier.getAddress().getPostalCode())
                                .region(carrier.getAddress().getRegion())
                                .settlement(carrier.getAddress().getSettlement())
                                .build(carrier.getAddress().getBuild())
                                .ogrnNumber(carrier.getAddress().getOgrnNumber())
                                .build())
                        .build()))
                .subsequentCarrier((Individuals.builder()
                        .organizationName(subsequentCarrier.getOrganizationName())
                        .legalAddress(subsequentCarrier.getLegalAddress())
                        .phone(subsequentCarrier.getPhone())
                        .bankCode(subsequentCarrier.getBankCode())
                        .bankName(subsequentCarrier.getBankName())
                        .roleIndividuals(RoleIndividuals.SUBSEQUENTCARRIER)
                        .address(Address.builder()
                                .city(subsequentCarrier.getAddress().getCity())
                                .postalCode(subsequentCarrier.getAddress().getPostalCode())
                                .region(subsequentCarrier.getAddress().getRegion())
                                .settlement(subsequentCarrier.getAddress().getSettlement())
                                .build(subsequentCarrier.getAddress().getBuild())
                                .ogrnNumber(subsequentCarrier.getAddress().getOgrnNumber())
                                .build())
                        .build()))
                .carrierNotes(carrierNotes)
                .cargoReceivedDate(cargoReceivedDate)
                .cmrFilledDate(cmrFilledDate)
                .tractorRegistrationNumber(tractorRegistrationNumber)
                .trailerRegistrationNumber(trailerRegistrationNumber)
                .build();
    }
}
