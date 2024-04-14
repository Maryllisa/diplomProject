package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.declaration.ProductLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductLocationRepository extends JpaRepository<ProductLocation, Long> {
}
