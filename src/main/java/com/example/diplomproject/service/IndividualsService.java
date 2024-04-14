package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.repository.*;
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
    private final AddressRepository addressRepository;

    public List<Individuals> getAllSuppliers() {
        return individualsRepository.findAll();
    }

    public Individuals getSuppliers(String login) {
         return findRegistrationSupplier(login);
    }
    public Individuals findRegistrationSupplier(String login){
        Account account = accountRepository.findByLogin(login);
        Individuals supplier = new Individuals();
        List<Individuals> suppliers = accountRepository.findAllIndividualsByAccount(account);
        if (suppliers.isEmpty()){
            supplier = new Individuals();
        }
        supplier = suppliers.get(0);
        return supplier;
    }

    public void addNewCompany(IndividualsDTO individualsDTO, String login) {
        Individuals individuals = individualsDTO.build(RoleIndividuals.SUPPLIER);
        individuals.setRoleIndividuals(RoleIndividuals.SUPPLIER);
        individuals.setAccount(accountRepository.findByLogin(login));
        individuals.setAddress(addressRepository.save(individualsDTO.getAddress().build()));
        individualsRepository.save(individuals);
    }
}
