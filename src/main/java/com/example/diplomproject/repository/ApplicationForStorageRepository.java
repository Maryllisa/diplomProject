package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationForStorageRepository extends JpaRepository<ApplicationForStorage, Long> {
    List<ApplicationForStorage> findAllByAccount(Account byLogin);
}
