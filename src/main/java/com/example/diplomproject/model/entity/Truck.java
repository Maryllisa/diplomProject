package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.DriverDTO;
import com.example.diplomproject.model.dto.TruckDTO;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTruck;
    @Column
    private String registrationNumber;
    @Column
    @Enumerated(EnumType.STRING)
    private Brand brand;
    @Column
    private String model;
    @Column
    private int yearTruck;
    @OneToOne
    private Driver driver;
    @ManyToOne
    private Account account;

    public TruckDTO build() {
        return TruckDTO.builder()
                .idTruck(idTruck)
                .registrationNumber(registrationNumber)
                .brand(brand)
                .model(model)
                .yearTruck(yearTruck)
                .driver(DriverDTO.builder()
                        .name(driver.getName())
                        .LicenseNumber(driver.getLicenseNumber())
                        .build())
                .build();
    }
}
