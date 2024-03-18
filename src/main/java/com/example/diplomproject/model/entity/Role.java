package com.example.diplomproject.model.entity;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, EMPLOYEE, MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}