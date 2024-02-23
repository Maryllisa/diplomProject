package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String surname;
    @Column
    private String name;
    @Column
    private String patronymic;
    @Column
    private String email;
    @Column
    private String phone;
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Provider> providersContactPerson;
}
