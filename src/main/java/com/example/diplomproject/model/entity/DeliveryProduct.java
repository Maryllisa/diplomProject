package com.example.diplomproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliveryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeliveryProduct;
    @Column
    private double weightProduct;
    @Column
    private Date arrangeDate;
    @Column
    private String prodCondition;
    @Column
    private double deliveryEvalution;
    @OneToOne
    private ApplicationForStorage applicationForStorage;
    @Column
    @ElementCollection
    private Map<Long, Boolean> checkProduct = new HashMap<>();
    @ManyToOne
    private Account account;


}
