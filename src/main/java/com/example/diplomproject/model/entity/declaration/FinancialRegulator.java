package com.example.diplomproject.model.entity.declaration;
import com.example.diplomproject.model.dto.dtoForDeclaration.FinancialRegulatorDTO;
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
public class FinancialRegulator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFinancialRegulator;
    @Column
    private String innKpp;
    @Column
    private String organizationName;
    @Column
    private String country;
    @Column
    private String postalCode;
    @Column
    private String region;
    @Column
    private String locality;
    @Column
    private String street;
    @Column
    private String ogrn;

    public FinancialRegulator(String innKpp, String organizationName, String country, String postalCode, String region, String locality, String street, String ogrn) {
        this.innKpp = innKpp;
        this.organizationName = organizationName;
        this.country = country;
        this.postalCode = postalCode;
        this.region = region;
        this.locality = locality;
        this.street = street;
        this.ogrn = ogrn;
    }

    public FinancialRegulatorDTO build(){
        return new FinancialRegulatorDTO(innKpp, organizationName, country, postalCode, region, locality,street,ogrn);
    }
}
