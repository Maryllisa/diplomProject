package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualsRepository extends JpaRepository<Individuals, Long> {

    List<Individuals> findByAccount(Account account);
    Optional<Individuals> findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(String organizationName,
                                                                                              String taxId,
                                                                                              String registrationCode,
                                                                                              RoleIndividuals roleIndividuals);
}
