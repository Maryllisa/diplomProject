package com.example.diplomproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Otpusk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private double sumForStorage;
    @OneToOne
    private ApplicationForRelease applicationForRelease;
    @Column
    @Lob
    private String src;
    @Column
    private String nameSrc;
    @Column
    private String type;
    @Column
    private long size;
    @OneToOne
    private MarkForAgency markForAgency;
}
