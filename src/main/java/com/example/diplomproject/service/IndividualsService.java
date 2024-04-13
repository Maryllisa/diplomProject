package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.repository.CRMRepository;
import com.example.diplomproject.repository.DeclarationTDRepository;
import com.example.diplomproject.repository.IndividualsRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualsService {

    private final IndividualsRepository individualsRepository;
    private final UserRepository accountRepository;
    private final DeclarationTDRepository declarationTDRepository;
    private final CRMRepository crmRepository;

    public List<Individuals> getAllSuppliers() {
        return individualsRepository.findAll();
    }

    public Individuals getSuppliers(String login) {
         return findRegistrationSupplier(login);
    }
    public Individuals findRegistrationSupplier(String login){
        Account account = accountRepository.findByLogin(login);
        Individuals supplier = declarationTDRepository.findIndividualsByAccountAndeRole(account).orElse(null);
        if (supplier==null){
            supplier = crmRepository.findIndividualsByAccountAndeRole(account).orElse(null);
            if (supplier==null){
                supplier = individualsRepository.findByAccount(account).orElse(null);
                if (supplier==null) supplier = new Individuals();
            }
        }
        return supplier;
    }
}
