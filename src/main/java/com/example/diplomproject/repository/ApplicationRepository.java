package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.StatusApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationForStorage, Long> {
    List<ApplicationForStorage> findByStatusApplication(StatusApplication statusApplication);

}
