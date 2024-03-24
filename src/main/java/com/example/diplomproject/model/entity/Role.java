package com.example.diplomproject.model.entity;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, CLIENT, MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}