package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;
    @Column
    private String nameProduct;

    @Column
    private String productCode;

    @Column
    private String originCountryCode;

    @Column
    private double grossWeight;
    @Column
    private Date finalDate;
    @Column
    private Date date;
    @Column
    private String preference;
    @Column(name = "procedure_name") // Измененное имя столбца
    private String procedure;
    @Column
    private double netWeight;
    @Column
    private String quota;
    @Column
    private Boolean isDelivery = false;
    @JsonIgnore
    @ManyToOne
    private DeclarationTD declarationTD;
    @JsonIgnore
    @OneToOne
    private ApplicationForRelease applicationForRelease;
    @JsonIgnore
    @OneToOne
    private ApplicationForStorage applicationForStorage;

    public ProductDTO build() {
        return ProductDTO.builder()
                .idProduct(idProduct)
                .nameProduct(nameProduct)
                .finalDate(finalDate)
                .date(date)
                .productCode(productCode)
                .originCountryCode(originCountryCode)
                .grossWeight(grossWeight)
                .preference(preference)
                .procedure(procedure)
                .netWeight(netWeight)
                .quota(quota)
                .numberDeclaration(declarationTD.getDeclarationNumber())
                .build();
    }
}