package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAddress;
    @Column
    private String city;
    @Column
    private String postalCode;
    @Column
    private String region;
    @Column
    private String settlement;
    @Column
    private String build;
    @Column
    private String ogrnNumber;


    public Address(String city, String postalCode, String region, String settlement, String build, String ogrnNumber) {
        this.city = city;
        this.postalCode = postalCode;
        this.region = region;
        this.settlement = settlement;
        this.build = build;
        this.ogrnNumber = ogrnNumber;
    }

    public AddressDTO build() {
        return new AddressDTO(city, postalCode, region, settlement, build, ogrnNumber);
    }
}
