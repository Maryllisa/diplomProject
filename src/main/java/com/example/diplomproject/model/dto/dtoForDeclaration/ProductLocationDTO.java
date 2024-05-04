package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.ProductLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductLocationDTO {
    @NotNull
    @NotEmpty
    private String uzoRegistry;
    @NotNull
    @NotEmpty
    private String customsCode;
    @NotNull
    @NotEmpty
    private String type;
    @NotNull
    @NotEmpty
    private String quantity;
    @NotNull
    @NotEmpty
    private String documentNumber;
    @NotNull
    @NotEmpty
    private Date date;
    @NotNull
    @NotEmpty
    private String ztkNumber;
    @NotNull
    @NotEmpty
    private String transportType;
    @NotNull
    @NotEmpty
    private String vehicleNumber;
    @NotNull
    @NotEmpty
    private String stationOrPort;
    @NotNull
    @NotEmpty
    private String country;
    @NotNull
    @NotEmpty
    private String postalCode;
    @NotNull
    @NotEmpty
    private String regionOrDistrict;
    @NotNull
    @NotEmpty
    private String locality;
    @NotNull
    @NotEmpty
    private String houseNumber;

    public ProductLocation build() {
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
