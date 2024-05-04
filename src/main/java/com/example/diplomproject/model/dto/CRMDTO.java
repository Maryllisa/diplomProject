package com.example.diplomproject.model.dto;

import com.example.diplomproject.config.annotation.imp.ValidDate;
import com.example.diplomproject.config.annotation.imp.ValidDateFetcher;
import com.example.diplomproject.model.entity.CRM;
import com.example.diplomproject.model.entity.CustomsProcessing;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CRMDTO {
    private Long idCRM;
    private IndividualsDTO sender;
    private IndividualsDTO resipient;
    /*
    * * * Место разгрузки
    */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String countryWH;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String cityWH;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String streetWH;
    @NotNull
    @NotEmpty
    private String houseNumberWH;
    /*
     * * * Место и дата погрузки
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String loadingCountry;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String loadingCity;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String loadingStreet;
    @NotNull
    @NotEmpty
    private String loadingHouseNumber;
    @NotNull
    @ValidDateFetcher
    private Date loadingDate;
    /*
     * * * Прилагаемые документы
     */
    @NotNull
    @NotEmpty
    private String invoiceDocument;
    @NotNull
    @NotEmpty
    private String shippingSpecificationDocument;
    @NotNull
    @NotEmpty
    private String qualityCertificateDocument;
    @NotNull
    @NotEmpty
    private String veterinaryCertificateDocument;
    @NotNull
    @NotEmpty
    private String quarantineCertificateDocument;
    @NotNull
    @NotEmpty
    private String certificateOfOriginDocument;
    @NotNull
    @NotEmpty
    private String loadingCertificateDocument;
    /*
     * * * 6-9
     */
    @NotNull
    @NotEmpty
    private String cargoQuantity;
    @NotNull
    @NotEmpty
    private String cargoName;
    @NotNull
    @NotEmpty
    private String nackagingType;
    @NotNull
    @NotEmpty
    private String numbers;
    /*
     * * * Статистический код
     */
    @NotNull
    @NotEmpty
    private String statistikCode;
    /*
     * * * Суммарный вес брутто
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[-+]?\\d*\\.\\d+$")

    private String grossWeight;
    /*
     * * * Объем
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[-+]?\\d*\\.\\d+$")
    private String volume;
    /*
     * * * Таможенная обработка
     */
    private CustomsProcessingDTO customsProcessing;
    /*
     * * * Возврат
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String returnCountry;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String returnCity;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String returnStreet;
    @NotNull
    @NotEmpty
    private String returnHouse;
    /*
     * * * Условия оплаты
    */
    @NotNull
    @NotEmpty
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
    @NotNull
    @NotEmpty
    private String carrierNotes;
    @NotNull
    @ValidDateFetcher
    private Date cargoReceivedDate;
    @NotNull
    @ValidDate
    private Date cmrFilledDate;
    /*
     * * * Регистрационные номера
     */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-ZА-Я]{2}\\d{4}[A-ZА-Я]{2}$")
    private String tractorRegistrationNumber;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\d{6}$")
    private String trailerRegistrationNumber;

    public CRM build() {
        return CRM.builder()
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
