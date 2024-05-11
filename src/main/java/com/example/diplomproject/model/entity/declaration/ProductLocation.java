package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.dtoForDeclaration.ProductLocationDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProductLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String uzoRegistry;

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
        return ProductLocationDTO.builder()
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


    public boolean check() {
        return this.uzoRegistry != null
                && this.customsCode != null
                && this.type != null
                && this.quantity != null
                && this.documentNumber != null
                && this.date != null
                && this.ztkNumber != null
                && this.transportType != null
                && this.vehicleNumber != null
                && this.stationOrPort != null
                && this.country != null
                && this.postalCode != null
                && this.regionOrDistrict != null
                && this.locality != null
                && this.houseNumber != null;
    }
}
