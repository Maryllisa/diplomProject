package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.enumStatus.Role;
import com.example.diplomproject.model.entity.enumStatus.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {
    public boolean existsByLogin(String login);
    public boolean existsByEmail(String email);
    public boolean existsByLoginAndEmail(String login, String email);

    public  Account findByLogin(String login);
    @Query("SELECT sup FROM Account ac " +
            "INNER JOIN Individuals sup ON sup.account.id = ac.id " +
            "WHERE ac=?1 ")
    List<Individuals> findAllIndividualsByAccount(Account account);

    Account findByActivationCode(String code);
    List<Account> findByStatus(Status status);


}
