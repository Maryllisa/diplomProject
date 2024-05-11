package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForRelease;
import com.example.diplomproject.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationForReleaseRepository extends JpaRepository<ApplicationForRelease,  Long> {

    List<ApplicationForRelease> findAllByAccount(Account byLogin);

}
