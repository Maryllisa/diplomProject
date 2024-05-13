package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.DeliveryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryProductRepository extends JpaRepository<DeliveryProduct, Long> {
}
