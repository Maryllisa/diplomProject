package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.CRM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public CRM build(){
        return new CRM(sender.build(),
                resipient.build(),
                countryWH,
                cityWH,
                streetWH,
                houseNumberWH,
                loadingCountry,
                loadingCity,
                loadingStreet,
                loadingHouseNumber,
                loadingDate,
                invoiceDocument,
                shippingSpecificationDocument,
                qualityCertificateDocument,
                veterinaryCertificateDocument,
                quarantineCertificateDocument,
                certificateOfOriginDocument,
                loadingCertificateDocument,
                cargoQuantity,
                cargoName,
                nackagingType,
                numbers,
                statistikCode,
                grossWeight,
                volume,
                customsProcessing.build(),
                returnCountry,
                returnCity,
                returnStreet,
                returnHouse,
                paymentTerms,
                carrier.build(),
                subsequentCarrier.build(),
                carrierNotes,
                cargoReceivedDate,
                cmrFilledDate,
                tractorRegistrationNumber,
                trailerRegistrationNumber);
    }

}
