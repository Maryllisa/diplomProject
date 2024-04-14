package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.ProductLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductLocationDTO {
    private String uzoRegistry;
    private String customsCode;
    private String type;
    private String quantity;
    private String documentNumber;
    private Date date;
    private String ztkNumber;
    private String transportType;
    private String vehicleNumber;
    private String stationOrPort;
    private String country;
    private String postalCode;
    private String regionOrDistrict;
    private String locality;
    private String houseNumber;

    public ProductLocation build(){
        return ProductLocation.builder()
                .uzoRegistry(uzoRegistry)
                .customsCode(customsCode)
                .type(type)
                .quantity(quantity)
                .documentNumber(documentNumber)
                .date(date)
                .ztkNumber(ztkNumber)
                .transportType(transportType)
                .vehicleNumber(vehicleNumber)
                .stationOrPort(stationOrPort)
                .country(country)
                .postalCode(postalCode)
                .regionOrDistrict(regionOrDistrict)
                .locality(locality)
                .houseNumber(houseNumber)
                .build();
    }
}
