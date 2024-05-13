package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT pr FROM Product pr " +
            "INNER JOIN DeclarationTD dec ON dec.idDeclaration = pr.declarationTD.idDeclaration " +
            "WHERE dec.account =?1 ")
    List<Product> findAllByDeclarationAndAccount(Account account);

    List<Product> findAllByApplicationForReleaseIsNull();

    List<Product> findAllByDeclarationTD(DeclarationTD byId);
    @Query("SELECT pr FROM Product pr " +
            "INNER JOIN DeclarationTD d ON d.idDeclaration = pr.declarationTD.idDeclaration " +
            "INNER JOIN ApplicationForStorage ap ON ap.declarationTD.idDeclaration = d.idDeclaration " +
            "WHERE ap.idApplication =?1")
    List<Product> getAllByApplication(Long id);

    List<Product> findAllByIsDelivery(Boolean isDelivery);
}
