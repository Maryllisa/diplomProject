package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
    public boolean existsByLogin(String login);

    public  Account findByLogin(String login);
}
