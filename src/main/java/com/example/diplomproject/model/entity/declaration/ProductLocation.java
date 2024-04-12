package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.dtoForDeclaration.ProductLocationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String uzoRegistry;

    public ProductLocation(String uzoRegistry, String customsCode, String type, String quantity, String documentNumber,
                           Date date, String ztkNumber, String transportType, String vehicleNumber, String stationOrPort,
                           String country, String postalCode, String regionOrDistrict, String locality, String houseNumber) {
        this.uzoRegistry = uzoRegistry;
        this.customsCode = customsCode;
        this.type = type;
        this.quantity = quantity;
        this.documentNumber = documentNumber;
        this.date = date;
        this.ztkNumber = ztkNumber;
        this.transportType = transportType;
        this.vehicleNumber = vehicleNumber;
        this.stationOrPort = stationOrPort;
        this.country = country;
        this.postalCode = postalCode;
        this.regionOrDistrict = regionOrDistrict;
        this.locality = locality;
        this.houseNumber = houseNumber;
    }

    @Column
    private String customsCode;
    @Column
    private String type;
    @Column
    private String quantity;
    @Column
    private String documentNumber;
    @Column
    private Date date;
    @Column
    private String ztkNumber;
    @Column
    private String transportType;
    @Column
    private String vehicleNumber;
    @Column
    private String stationOrPort;
    @Column
    private String country;
    @Column
    private String postalCode;
    @Column
    private String regionOrDistrict;
    @Column
    private String locality;
    @Column
    private String houseNumber;

    public ProductLocationDTO build() {
        return new ProductLocationDTO(uzoRegistry,
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
