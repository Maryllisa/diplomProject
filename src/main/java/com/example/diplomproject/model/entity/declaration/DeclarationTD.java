package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDefs({
        @TypeDef(name = "addressType", typeClass = Address.class),
        @TypeDef(name = "financialRegulator", typeClass = FinancialRegulator.class),
        @TypeDef(name = "currencyRate", typeClass = CurrencyRate.class),
        @TypeDef(name = "productLocation", typeClass = ProductLocation.class)
})
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
    @OneToOne
    private Supplier supplier;
    @Column
    private String customsCode;
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
    @Column
    private String recipientCompany;
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.Address")
    Address recipientAddress;
    /*
        Лицо ответственное за финансовое регулирование
    */
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.FinancialRegulator")
    private FinancialRegulator financialRegulator;
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
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.Address")
    Address declarant;
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
    private String conditionsOfDeliveryName;
    /*
           Идентификация и страна регистрации активного транспортного средства на границе
           кол-во тс.
           Номера транспортного средства
    */
    @Column
    private String numberOfVehicles;
    private String vehicleNumbers;
    /*
           Валюта и общая сумма по счету
           Код валюты
           Сумма по счету
   */
    @Column
    private String currency;
    private String accountTotalAmount;
    /*
           Курс валюты
    */
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.CurrencyRate")
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
    @Column
    @Type(type = "com.example.diplomproject.model.entity.declaration.ProductLocation")
    private ProductLocation productLocation;
    /*
           Грузовые места и описание товаров
    */
    @Column
    private String productDescription;
    @OneToMany
    private List<Product> productList;



}
