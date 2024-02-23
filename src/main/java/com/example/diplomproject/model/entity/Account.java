package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account extends User {
    @Column
    private String surname;
    @Column
    private String name;
    @Column
    private String patronymic;
    @Column
    private Date dateBirthday;
    @Column
    private String phone;
    @Column
    private String position;
    @Column
    @Enumerated(EnumType.STRING)
    Department department;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "photoId")
    private Photo photo;
}
