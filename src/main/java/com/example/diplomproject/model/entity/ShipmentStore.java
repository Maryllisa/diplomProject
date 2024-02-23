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
public class ShipmentStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nameStore;
    @Column
    private Date date;
    @Column
    private String address;
    @Column
    private String status;
    @Column
    private float evaluationDelivery;
    @ManyToOne (fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="idContactPerson")
    private ContactPerson catalog;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Assortment> productAssortmentList;

}
