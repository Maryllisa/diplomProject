package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.GoodTransportDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodTransportDocumentRepository extends JpaRepository<GoodTransportDocument, Long> {
}
