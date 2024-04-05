package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
    public boolean existsByLogin(String login);
    public boolean existsByEmail(String email);
    public boolean existsByLoginAndEmail(String login, String email);

    public  Account findByLogin(String login);

    Account findByActivationCode(String code);
    List<Account> findByStatus(Status status);
}