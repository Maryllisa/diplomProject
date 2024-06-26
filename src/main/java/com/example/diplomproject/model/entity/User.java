package com.example.diplomproject.model.entity;

import javax.persistence.*;

import com.example.diplomproject.model.entity.enumStatus.Role;
import com.example.diplomproject.model.entity.enumStatus.Status;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String login;
    @Column
    private String email;
    @Column
    private String password;
    @Column(name = "active")
    private boolean active;
    @Column(name = "activation_code")
    private String activationCode;
    @Column(name = "activation_email")
    private boolean activationEmail;
    @Column(name = "reset_password_token")
    private String reset_password_token;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role_admin", joinColumns = @JoinColumn(name = "id_admin"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Status status;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
    public void setRoles(Role role) {
        this.roles.add(role);
    }
}
