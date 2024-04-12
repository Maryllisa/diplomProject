package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.declaration.FinancialRegulator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialRegulatorRepository extends JpaRepository<FinancialRegulator,Long> {
}
