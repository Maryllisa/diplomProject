package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.CRM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRMRepository extends JpaRepository<CRM, Long> {
}
