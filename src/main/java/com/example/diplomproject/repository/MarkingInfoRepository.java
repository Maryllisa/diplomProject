package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.MarkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkingInfoRepository extends JpaRepository<MarkingInfo, Long> {
}
