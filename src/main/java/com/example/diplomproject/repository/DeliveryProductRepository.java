package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.DeliveryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryProductRepository extends JpaRepository<DeliveryProduct, Long> {
    List<DeliveryProduct> findAllByAccount(Account account);
    @Query("SELECT d FROM DeliveryProduct d " +
            "INNER JOIN ApplicationForStorage ap ON ap = d.applicationForStorage " +
            "WHERE ap.account = ?1")
    List<DeliveryProduct> findAllByClient(Account byLogin);
}
