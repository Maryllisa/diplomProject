package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.CustomsProcessing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomsProcessingRepository extends JpaRepository<CustomsProcessing, Long> {
}
