package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nameProvider;
    @Column
    private String address;
    @Column
    private String build;
    @ManyToOne (fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="idContactPerson")
    private ContactPerson contactPerson;
    @Column
    private float evaluationProvider;
    @OneToOne (fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="idDeliveryWarehouse")
    private ContactPerson deliveryWarehouse;

}
