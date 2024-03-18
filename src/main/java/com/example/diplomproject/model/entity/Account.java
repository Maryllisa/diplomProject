package com.example.diplomproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "photoId")
    private Photo photo;

}
