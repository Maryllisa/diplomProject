package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodTransportDocumentRepository extends JpaRepository<GoodTransportDocument, Long> {
    List<GoodTransportDocument> findAllByAccount(Account account);
}
