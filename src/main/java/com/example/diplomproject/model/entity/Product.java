package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    @Column
    private int itemNumber;

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
}