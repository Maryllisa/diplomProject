package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;

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
    private int itemNumber;
    @Column
    private String nameProduct;

    @Column
    private String productCode;

    @Column
    private String originCountryCode;

    @Column
    private double grossWeight;

    @Column
    private String preference;

    @Column(name = "procedure_name") // Измененное имя столбца
    private String procedure;

    @Column
    private double netWeight;

    @Column
    private String quota;
    @ManyToOne
    private DeclarationTD declarationTD;

    public ProductDTO build() {
        return ProductDTO.builder()
                .idProduct(idProduct)
                .itemNumber(itemNumber)
                .nameProduct(nameProduct)
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