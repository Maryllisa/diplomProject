package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.ApplicationForStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationForStorageRepository extends JpaRepository<ApplicationForStorage, Long> {
}
