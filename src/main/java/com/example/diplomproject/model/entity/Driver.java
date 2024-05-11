package com.example.diplomproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String LicenseNumber;

    public boolean check() {
        return this.name!=null && this.getLicenseNumber()!=null;
    }
}
