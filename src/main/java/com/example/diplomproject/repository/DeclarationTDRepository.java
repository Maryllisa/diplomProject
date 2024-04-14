package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeclarationTDRepository extends JpaRepository<DeclarationTD, String> {

    boolean existsByDeclarationNumber(String declarationNumber);
    @Query("SELECT sup FROM DeclarationTD dec " +
            "inner join Individuals sup on sup.idSupplier = dec.individuals.idSupplier " +
            "where dec.account = ?1")
    Optional<Individuals> findIndividualsByAccountAndeRole(Account account);
    @Query("SELECT sup FROM DeclarationTD dec " +
            "inner join Individuals sup on sup.idSupplier = dec.recipientAddress.idSupplier " +
            "where dec.account = ?1")
    Optional<Individuals> findRecipientByAccountAndeRole(Account account);
}
