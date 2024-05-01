package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.MarkingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkingInfoRepository extends JpaRepository<MarkingInfo, Long> {
    @Query("SELECT alls FROM MarkingInfo alls " +
            "INNER JOIN ApplicationForMarking ap ON ap.idApplicationForMarking = alls.applicationForMarking.idApplicationForMarking " +
            "WHERE ap.account =?1")
    List<MarkingInfo> findAllByAccount(Account account);
}
