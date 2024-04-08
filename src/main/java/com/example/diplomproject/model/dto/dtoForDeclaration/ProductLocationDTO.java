package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.ProductLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
        return new ProductLocation(uzoRegistry,
                customsCode,
                type,
                quantity,
                documentNumber,
                date,
                ztkNumber,
                transportType,
                vehicleNumber,
                stationOrPort,
                country,
                postalCode,
                regionOrDistrict,
                locality,
                houseNumber);
    }
}
