package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationForStorageRepository extends JpaRepository<ApplicationForStorage, Long> {
    List<ApplicationForStorage> findAllByAccount(Account byLogin);
    List<ApplicationForStorage> findAllByAccountManager(Account byLogin);
    List<ApplicationForStorage> findAllByAccountManagerAndStatusApplication(Account accountManager, StatusApplication statusApplication);
}
