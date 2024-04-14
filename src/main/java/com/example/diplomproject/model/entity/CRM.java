package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.CRMDTO;
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
        return new CRMDTO(sender.buildDTO(),
                resipient.buildDTO(),
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
                carrier.buildDTO(),
                subsequentCarrier.buildDTO(),
                carrierNotes,
                cargoReceivedDate,
                cmrFilledDate,
                tractorRegistrationNumber,
                trailerRegistrationNumber);
    }
}
