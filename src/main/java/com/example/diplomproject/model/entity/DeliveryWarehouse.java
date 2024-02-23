package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DeliveryWarehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Date date;
    @Column
    private String status;
    @Column
    private float evaluationDelivery;
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "provider")
    private Provider provider;
    @OneToMany(fetch = FetchType.EAGER,
                cascade = CascadeType.ALL)
    private List<Assortment> productAssortmentList;




}
