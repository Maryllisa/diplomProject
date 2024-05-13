package com.example.diplomproject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
    @OneToOne(fetch = FetchType.EAGER)
    private ApplicationForStorage applicationForStorage;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> productList = new ArrayList<>();
    @ManyToOne
    private Account account;


}
