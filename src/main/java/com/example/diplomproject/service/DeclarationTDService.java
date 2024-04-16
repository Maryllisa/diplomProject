package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.declaration.ProductLocation;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class DeclarationTDService {
    private final DeclarationTDRepository declarationTDRepository;
    private final UserRepository userRepository;
    private final IndividualsRepository individualsRepository;
    private final ProductService productService;
    private final AddressRepository addressRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final IndividualsService individualsService;
    private final ProductLocationRepository productLocationRepository;

    public DeclarationDTO geFormForNewDeclaration(String login) { // переписать логику
        DeclarationDTO declarationDTO = new DeclarationDTO();
        Account account = userRepository.findByLogin(login);
        Individuals supplier = individualsService.findRegistrationSupplier(login);
        declarationDTO.setSenderDTO(supplier.build(RoleIndividuals.CARRIER));
        return declarationDTO;

    }
    public Map<String, String> checkNewDeclaration(BindingResult result, DeclarationDTO declarationDTO) {
        return null;
    }
    public void addNewDeclaration(DeclarationDTO declarationDTO, String login){
        log.info("Регистрация декларации");
        DeclarationTD declarationTDForDB = declarationDTO.build();
        Account account = userRepository.findByLogin(login);
        declarationTDForDB.setAccount(account);
        log.info("Регистрация декларации на клиенте: " + login);
        Individuals individualsFromDB = new Individuals();
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(declarationDTO.getSenderDTO().getOrganizationName(),
                declarationDTO.getSenderDTO().getTaxId(), declarationDTO.getSenderDTO().getRegistrationCode(), RoleIndividuals.SUPPLIER).isEmpty()){
            Individuals individuals = declarationTDForDB.getIndividuals();
            individuals.setAccount(account);
            individuals.setAddress(addressRepository.save(declarationTDForDB.getIndividuals().getAddress()));
            individualsFromDB = individualsRepository.save(individuals);
        }
        else {
            log.info("Регистрация декларации нового поставщика: " + declarationDTO.getSenderDTO().build(RoleIndividuals.SUPPLIER));
            Individuals individuals = declarationTDForDB.getIndividuals();
            individuals.setAccount(account);
            individuals.setAddress(addressRepository.save(declarationTDForDB.getIndividuals().getAddress()));
            individualsFromDB = individualsRepository.save(individuals);
        }
        declarationTDForDB.setIndividuals(individualsFromDB);

        Individuals individuals = declarationTDForDB.getRecipientAddress();
        individuals.setAddress(addressRepository.save(declarationTDForDB.getRecipientAddress().getAddress()));
        declarationTDForDB.setRecipientAddress(individualsRepository.save(individuals));

        individuals = declarationTDForDB.getFinancialRegulator();
        individuals.setAddress(addressRepository.save(declarationTDForDB.getFinancialRegulator().getAddress()));
        declarationTDForDB.setFinancialRegulator(individualsRepository.save(individuals));

        individuals = declarationTDForDB.getDeclarant();
        individuals.setAddress(addressRepository.save(declarationTDForDB.getDeclarant().getAddress()));
        declarationTDForDB.setDeclarant(individualsRepository.save(individuals));

        declarationTDForDB.setCurrencyRate(currencyRateRepository.save(declarationTDForDB.getCurrencyRate()));

        declarationTDForDB.setProductLocation(productLocationRepository.save(declarationTDForDB.getProductLocation()));

        log.info("Регистрация курса: " + declarationDTO.getCurrencyRateDTO().build());
        declarationTDForDB.setCurrencyRate(currencyRateRepository.save(declarationTDForDB.getCurrencyRate()));
        declarationTDForDB = declarationTDRepository.save(declarationTDForDB);
        declarationTDForDB.setProductList(productService.addNewProduct(declarationTDForDB, declarationDTO.getProductDTOS()));
        declarationTDForDB.setAccount(account);
        declarationTDRepository.save(declarationTDForDB);
        log.info("Завершение регистрации: " + declarationTDForDB.getDeclarationNumber());
    }

    public List<DeclarationTD> findAllByAccount(String name) {
        Account account = userRepository.findByLogin(name);
        return  declarationTDRepository.findAllByAccount(account);
    }

    public List<Individuals> getSupplier(String login) {
        return individualsRepository.findByAccount(userRepository.findByLogin(login));
    }
}
