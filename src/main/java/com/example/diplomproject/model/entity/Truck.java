package com.example.diplomproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToOne
    private Individuals individuals;
}
