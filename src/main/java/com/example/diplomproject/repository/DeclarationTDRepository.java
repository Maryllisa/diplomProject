package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationTDRepository extends JpaRepository<DeclarationTD, String> {

    boolean existsByDeclarationNumber(String declarationNumber);
}
