package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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

    public DeclarationDTO geFormForNewDeclaration(String login) { // переписать логику
        DeclarationDTO declarationDTO = new DeclarationDTO();
        Account account = userRepository.findByLogin(login);
        Individuals supplier = individualsService.findRegistrationSupplier(login);
        declarationDTO.setSenderDTO(supplier.buildDTO());
        return declarationDTO;

    }
    public Map<String, String> checkNewDeclaration(BindingResult result, DeclarationDTO declarationDTO) {
        return null;
    }
    public void addNewDeclaration(DeclarationDTO declarationDTO, String login){
        log.info("Регистрация декларации");
        DeclarationTD declarationTDForDB = declarationDTO.buildWithoutEntity();
        Account account = userRepository.findByLogin(login);
        declarationTDForDB.setAccount(account);
        log.info("Регистрация декларации на клиенте: " + login);
        Individuals individualsFromDB = new Individuals();
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(declarationDTO.getSenderDTO().getOrganizationName(),
                declarationDTO.getSenderDTO().getTaxId(), declarationDTO.getSenderDTO().getRegistrationCode(), RoleIndividuals.SUPPLIER).isEmpty()){
            Individuals individuals = declarationDTO.getSenderDTO().build();
            individuals.setRoleIndividuals(RoleIndividuals.SUPPLIER);
            individuals.setAccount(account);
            individuals.setAddress(addressRepository.save(declarationDTO.getSenderDTO().getAddress().build()));
            individualsFromDB = individualsRepository.save(individuals);
        }
        else {
            log.info("Регистрация декларации нового поставщика: " + declarationDTO.getSenderDTO().build());
            Individuals individuals = declarationDTO.getSenderDTO().build();
            individuals.setRoleIndividuals(RoleIndividuals.SUPPLIER);
            individuals.setAddress(addressRepository.save(declarationDTO.getSenderDTO().getAddress().build()));
            individualsFromDB = individualsRepository.save(individuals);
        }
        declarationTDForDB.setIndividuals(individualsFromDB);
        Individuals individuals = declarationDTO.getSenderDTO().build();
        individuals.setRoleIndividuals(RoleIndividuals.RECIPIENT);
        individuals.setAddress(addressRepository.save(declarationDTO.getSenderDTO().getAddress().build()));
        declarationTDForDB.setRecipientAddress(individualsRepository.save(individuals));

        individuals = declarationDTO.getSenderDTO().build();
        individuals.setRoleIndividuals(RoleIndividuals.FINYREG);
        individuals.setAddress(addressRepository.save(declarationDTO.getSenderDTO().getAddress().build()));
        declarationTDForDB.setFinancialRegulator(individualsRepository.save(individuals));

        individuals = declarationDTO.getSenderDTO().build();
        individuals.setRoleIndividuals(RoleIndividuals.DECLARANT);
        individuals.setAddress(addressRepository.save(declarationDTO.getSenderDTO().getAddress().build()));
        declarationTDForDB.setDeclarant(individualsRepository.save(individuals));
        if (!declarationDTO.isFreeDeliveryCheckbox()){
            declarationTDForDB.setCurrency(declarationDTO.getCurrencyCode());
            declarationTDForDB.setAccountTotalAmount(declarationDTO.getInvoiceAmount());
        }
        else {
            declarationTDForDB.setCurrency("");
            declarationTDForDB.setAccountTotalAmount("");
        }
        log.info("Регистрация курса: " + declarationDTO.getCurrencyRateDTO().build());
        declarationTDForDB.setCurrencyRate(currencyRateRepository.save(declarationDTO.getCurrencyRateDTO().build()));
        declarationTDForDB = declarationTDRepository.save(declarationTDForDB);
        declarationTDForDB.setProductList(productService.addNewProduct(declarationTDForDB, declarationDTO.getProductDTOS()));
        log.info("Завершение регистрации: " + declarationTDForDB.getDeclarationNumber());
    }
}
