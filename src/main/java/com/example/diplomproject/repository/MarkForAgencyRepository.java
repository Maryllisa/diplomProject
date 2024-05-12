package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.MarkForAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkForAgencyRepository extends JpaRepository<MarkForAgency, Long> {
}
