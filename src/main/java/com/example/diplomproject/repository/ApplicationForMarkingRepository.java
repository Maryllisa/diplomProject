package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationForMarkingRepository extends JpaRepository<ApplicationForMarking, Long> {
    Optional<List<ApplicationForMarking>> findAllByAccount(Account byLogin);
}
