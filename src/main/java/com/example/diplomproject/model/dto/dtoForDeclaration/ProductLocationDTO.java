package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.ProductLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductLocationDTO {
    @NotEmpty
    private String uzoRegistry;
    @NotEmpty
    private String customsCode;
    @NotEmpty
    private String type;
    @NotEmpty
    private String quantity;
    @NotEmpty
    private String documentNumber;
    @NotEmpty
    private Date date;
    @NotEmpty
    private String ztkNumber;
    @NotEmpty
    private String transportType;
    @NotEmpty
    private String vehicleNumber;
    @NotEmpty
    private String stationOrPort;
    @NotEmpty
    private String country;
    @NotEmpty
    private String postalCode;
    @NotEmpty
    private String regionOrDistrict;
    @NotEmpty
    private String locality;
    @NotEmpty
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
