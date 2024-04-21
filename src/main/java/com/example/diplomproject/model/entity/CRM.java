package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.CustomsProcessingDTO;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
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
                .idCRM(idCRM)
                .sender(sender.build(RoleIndividuals.SUPPLIER))
                .resipient(resipient.build(RoleIndividuals.RECIPIENT))
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
                .carrier(carrier.build(RoleIndividuals.CARRIER))
                .subsequentCarrier(carrier.build(RoleIndividuals.SUBSEQUENTCARRIER))
                .carrierNotes(carrierNotes)
                .cargoReceivedDate(cargoReceivedDate)
                .cmrFilledDate(cmrFilledDate)
                .tractorRegistrationNumber(tractorRegistrationNumber)
                .trailerRegistrationNumber(trailerRegistrationNumber)
                .build();
    }
}
