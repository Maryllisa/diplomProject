package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResultEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER,
                cascade = CascadeType.ALL)
    @JoinColumn(name = "idAssortment")
    private Assortment assortment;
    @OneToMany(fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<CriteriaEvaluation> criteriaEvaluations;
    @Column
    private float evaluationProduct;

}
