package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.DeliveryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryProductRepository extends JpaRepository<DeliveryProduct, Long> {
    List<DeliveryProduct> findAllByAccount(Account account);
}
