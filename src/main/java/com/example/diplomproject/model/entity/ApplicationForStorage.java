package com.example.diplomproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ApplicationForStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplication;
    @OneToOne
    private Truck truck;
    @Column
    private int countPositionProducts;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusApplication statusApplication;
    @OneToOne
    private DeclarationTD declarationTD;
    @OneToOne
    private Supplier supplier;

}
