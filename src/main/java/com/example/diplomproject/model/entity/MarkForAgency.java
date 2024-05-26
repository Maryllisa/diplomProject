package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.entity.enumStatus.TypeEvaluation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MarkForAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarkForAgency;
    @Column
    @Enumerated(EnumType.STRING)
    private TypeEvaluation typeEvaluation;
    @Column
    private double evaluation;
    @Column
    private double weightCoefficient;
    @OneToOne
    private Account client;
    @JsonIgnore
    @ManyToOne
    private CustomsAgency customsAgency;

}
