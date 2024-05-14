package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForRelease;
import com.example.diplomproject.model.entity.Otpusk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpuskRepository extends JpaRepository<Otpusk, Long> {
    @Query("SELECT ot FROM Otpusk ot " +
            "INNER JOIN ApplicationForRelease ap ON ot.applicationForRelease = ap " +
            "WHERE ap.account =?1")
    List<Otpusk> findAllByApplicationForRelease(Account account);
    Otpusk findAllByApplicationForRelease(ApplicationForRelease applicationForRelease);
@Query("SELECT o FROM Otpusk o " +
        "INNER JOIN ApplicationForRelease ap ON ap = o.applicationForRelease " +
        "WHERE ap.account =?1 ")
    List<Otpusk> findAllByClient(Account byLogin);
}
