package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.entity.enumStatus.TypeEvaluation;
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
    private TypeEvaluation typeEvaluation;
    @Column
    private double evaluation;
    @Column
    private double weightCoefficient;
    @OneToOne
    private Account client;
    @OneToOne
    private Account employee;
    @ManyToOne
    private CustomsAgency customsAgency;

}
