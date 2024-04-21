package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.enumStatus.Brand;
import com.example.diplomproject.model.entity.Driver;
import com.example.diplomproject.model.entity.Truck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TruckDTO {
    private Long idTruck;
    private String registrationNumber;
    private Brand brand;
    private String model;
    private int yearTruck;
    private DriverDTO driver;

    public Truck build() {
        return Truck.builder()
                .idTruck(idTruck)
                .registrationNumber(registrationNumber)
                .brand(brand)
                .model(model)
                .yearTruck(yearTruck)
                .driver(Driver.builder()
                        .name(driver.getName())
                        .LicenseNumber(driver.getLicenseNumber())
                        .build())
                .build();
    }
}
