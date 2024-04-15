package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.CRM;
import com.example.diplomproject.model.entity.Individuals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CRMRepository extends JpaRepository<CRM, Long> {
    @Query("SELECT sup FROM CRM crm " +
            "INNER JOIN Individuals sup ON sup.idSupplier = crm.sender.idSupplier " +
            "WHERE crm.account = ?1")
    Optional<Individuals> findIndividualsByAccountAndeRole(Account account);

    List<CRM> findAllByAccount(Account account);
}
