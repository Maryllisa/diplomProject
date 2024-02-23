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
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nameCategory;
    @Column
    private String descriptionCategory;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Product> productsAssortment;
}
