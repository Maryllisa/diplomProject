package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.CRM;
import com.example.diplomproject.model.entity.CustomsProcessing;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
