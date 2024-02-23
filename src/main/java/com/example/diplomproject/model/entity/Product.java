package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @ElementCollection
    @Column
    private List<String> composition;
    @Column
    private float weightProduct;
    @Column
    @Enumerated(EnumType.STRING)
    Conditions conditions;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Assortment> productsAssortment;
    @ManyToOne (fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="idCatalog")
    private Catalog catalog;
    @OneToOne(fetch = FetchType.EAGER,
             cascade = CascadeType.ALL)
    @JoinColumn(name = "certificateId")
    private Certificate certificate;
}
