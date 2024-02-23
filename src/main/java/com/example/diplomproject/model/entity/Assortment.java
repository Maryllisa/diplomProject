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
public class Assortment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne (fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="idProduct")
    private Product product;
    @Column
    private Date dateMFD;
    @Column
    private Date dateExpiration;
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<CriteriaEvaluation> criteriaEvaluationList;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Review> reviews;

}
