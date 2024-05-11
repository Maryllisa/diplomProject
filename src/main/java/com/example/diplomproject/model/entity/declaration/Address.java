package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public AddressDTO build(){
        return AddressDTO.builder()
                .city(city)
                .postalCode(postalCode)
                .region(region)
                .settlement(settlement)
                .build(build)
                .ogrnNumber(ogrnNumber)
                .build();
    }

    public void change(AddressDTO address) {
        this.city = address.getCity();
        this.ogrnNumber = address.getOgrnNumber();
        this.postalCode = address.getPostalCode();
        this.region = address.getRegion();
        this.settlement = address.getSettlement();
        this.build = address.getBuild();
    }

    public boolean check() {
        return this.city!=null
                && this.ogrnNumber!=null
                && this.postalCode!=null
                && this.region!=null
                && this.settlement!=null
                && this.build!=null;
    }
}
