package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.CustomsProcessingDTO;
import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.declaration.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CRM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCRM;
    @OneToOne
    private Individuals sender;
    @OneToOne
    private Individuals resipient;
    /*
    * * * Место разгрузки
    */
    @Column
    private String countryWH;
    @Column
    private String cityWH;
    @Column
    private String streetWH;
    @Column
    private String houseNumberWH;
    /*
     * * * Место и дата погрузки
     */
    @Column
    private String loadingCountry;
    @Column
    private String loadingCity;
    @Column
    private String loadingStreet;
    @Column
    private String loadingHouseNumber;
    @Column
    private Date loadingDate;
    /*
     * * * Прилагаемые документы
     */
    @Column
    private String invoiceDocument;
    @Column
    private String shippingSpecificationDocument;
    @Column
    private String qualityCertificateDocument;
    @Column
    private String veterinaryCertificateDocument;
    @Column
    private String quarantineCertificateDocument;
    @Column
    private String certificateOfOriginDocument;
    @Column
    private String loadingCertificateDocument;
    /*
     * * * 6-9
     */
    @Column
    private String cargoQuantity;
    @Column
    private String cargoName;
    @Column
    private String nackagingType;
    @Column
    private String numbers;
    /*
     * * * Статистический код
     */
    @Column
    private String statistikCode;
    /*
     * * * Суммарный вес брутто
     */
    @Column
    private String grossWeight;
    /*
     * * * Объем
     */
    @Column
    private String volume;
    /*
     * * * Таможенная обработка
     */
    @OneToOne
    private CustomsProcessing customsProcessing;
    /*
     * * * Возврат
     */
    @Column
    private String returnCountry;
    @Column
    private String returnCity;
    @Column
    private String returnStreet;
    @Column
    private String returnHouse;
    @Column
    /*
     * * * Условия оплаты
    */
    private String paymentTerms;
    /*
     * * * Перевозчик
     */
    @OneToOne
    private Individuals carrier;
    /*
     * * * Последующий перевозчик
     */
    @OneToOne
    private Individuals subsequentCarrier;
    /*
     * * * Оговорки и замечания перевозчика
     */
    @Column
    private String carrierNotes;
    /*
     * * * Дата получения груза
     */
    @Column
    private String cargoReceivedDate;
    /*
     * * * Дата заполнения СМR
     */
    @Column
    private Date cmrFilledDate;
    /*
     * * * Регистрационные номера
     */
    @Column
    private String tractorRegistrationNumber;
    @Column
    private String trailerRegistrationNumber;
    @ManyToOne
    private Account account;
    public CRMDTO build(){
        return CRMDTO.builder()
                .sender(IndividualsDTO.builder()
                        .organizationName(sender.getOrganizationName())
                        .legalAddress(sender.getLegalAddress())
                        .phone(sender.getPhone())
                        .bankCode(sender.getBankCode())
                        .bankName(sender.getBankName())
                        .roleIndividuals(RoleIndividuals.SUPPLIER)
                        .address(AddressDTO.builder()
                                .city(sender.getAddress().getCity())
                                .postalCode(sender.getAddress().getPostalCode())
                                .region(sender.getAddress().getRegion())
                                .settlement(sender.getAddress().getSettlement())
                                .build(sender.getAddress().getBuild())
                                .ogrnNumber(sender.getAddress().getOgrnNumber())
                                .build())
                        .build())
                .resipient(IndividualsDTO.builder()
                        .organizationName(resipient.getOrganizationName())
                        .legalAddress(resipient.getLegalAddress())
                        .phone(resipient.getPhone())
                        .bankCode(resipient.getBankCode())
                        .bankName(resipient.getBankName())
                        .roleIndividuals(RoleIndividuals.RECIPIENT)
                        .address(AddressDTO.builder()
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
                .customsProcessing(CustomsProcessingDTO.builder()
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
                .carrier((IndividualsDTO.builder()
                        .organizationName(carrier.getOrganizationName())
                        .legalAddress(carrier.getLegalAddress())
                        .phone(carrier.getPhone())
                        .bankCode(carrier.getBankCode())
                        .bankName(carrier.getBankName())
                        .roleIndividuals(RoleIndividuals.CARRIER)
                        .address(AddressDTO.builder()
                                .city(carrier.getAddress().getCity())
                                .postalCode(carrier.getAddress().getPostalCode())
                                .region(carrier.getAddress().getRegion())
                                .settlement(carrier.getAddress().getSettlement())
                                .build(carrier.getAddress().getBuild())
                                .ogrnNumber(carrier.getAddress().getOgrnNumber())
                                .build())
                        .build()))
                .subsequentCarrier((IndividualsDTO.builder()
                        .organizationName(subsequentCarrier.getOrganizationName())
                        .legalAddress(subsequentCarrier.getLegalAddress())
                        .phone(subsequentCarrier.getPhone())
                        .bankCode(subsequentCarrier.getBankCode())
                        .bankName(subsequentCarrier.getBankName())
                        .roleIndividuals(RoleIndividuals.SUBSEQUENTCARRIER)
                        .address(AddressDTO.builder()
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
