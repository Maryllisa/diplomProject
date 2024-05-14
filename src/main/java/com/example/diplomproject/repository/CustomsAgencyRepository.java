package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomsAgencyRepository extends JpaRepository<CustomsAgency, Long> {
    @Query("SELECT c FROM CustomsAgency c " +
            "INNER JOIN MarkForAgency m ON m.customsAgency = ?1 ")
    Optional<CustomsAgency> findAllByMarkForAgencies(MarkForAgency markForAgencies);

    CustomsAgency findAllByAccountAndMarkingInfo(Account account, MarkingInfo markingInfo);

    List<CustomsAgency> findAllByAccount(Account account);

    Optional<CustomsAgency> findByDeliveryProduct(DeliveryProduct deliveryProduct);
}
