package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.CustomsAgency;
import com.example.diplomproject.model.entity.MarkForAgency;
import com.example.diplomproject.model.entity.enumStatus.TypeEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarkForAgencyRepository extends JpaRepository<MarkForAgency, Long> {
    Optional<MarkForAgency> findAllByCustomsAgency(CustomsAgency customsAgency);
    Optional<MarkForAgency> findAllByCustomsAgencyAndTypeEvaluation(CustomsAgency customsAgency, TypeEvaluation typeEvaluation);


}
