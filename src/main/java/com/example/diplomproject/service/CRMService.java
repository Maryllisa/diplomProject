package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CRMService {
    private final CRMRepository crmRepository;
    private final CustomsProcessingRepository customsProcessingRepository;
    private final AddressRepository addressRepository;
    private final IndividualsRepository individualsRepository;
    private final UserRepository userRepository;
    private final IndividualsService individualsService;

    public static Map<String, String> checkNewCRM(BindingResult result, CRMDTO crmdto) {
        return new HashMap<>();
    }

    public void addNewCRM(CRMDTO crmdto, String login) {
        CRM crm = crmdto.build();
        Account account = userRepository.findByLogin(login);
        log.info("Проверка отправителя/поставщика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSender().getOrganizationName(),
                crmdto.getSender().getTaxId(), crmdto.getSender().getRegistrationCode(), RoleIndividuals.SUPPLIER).isEmpty()){
            Individuals individuals = crmdto.getSender().build();
            individuals.setAddress(addressRepository.save(crmdto.getSender().getAddress().build()));
            individuals.setRoleIndividuals(RoleIndividuals.SUPPLIER);
            individuals.setAccount(account);
            crm.setSender(individualsRepository.save(individuals));
        }
        else{
            crm.getSender().setAddress(addressRepository.save(crmdto.getSender().getAddress().build()));
            crm.setSender(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSender().getOrganizationName(),
                    crmdto.getSender().getTaxId(), crmdto.getSender().getRegistrationCode(), RoleIndividuals.SUPPLIER).orElse(null));
        }
        log.info("Проверка получателя");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getResipient().getOrganizationName(),
                crmdto.getResipient().getTaxId(), crmdto.getResipient().getRegistrationCode(), RoleIndividuals.RECIPIENT).isEmpty()){
            Individuals individuals = crmdto.getResipient().build();
            individuals.setAddress(addressRepository.save(crmdto.getResipient().getAddress().build()));
            individuals.setRoleIndividuals(RoleIndividuals.RECIPIENT);
            crm.setResipient(individualsRepository.save(individuals));
        }
        else{
            crm.getResipient().setAddress(addressRepository.save(crmdto.getResipient().getAddress().build()));
            crm.setResipient(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getResipient().getOrganizationName(),
                    crmdto.getResipient().getTaxId(), crmdto.getResipient().getRegistrationCode(), RoleIndividuals.RECIPIENT).orElse(null));
        }
        CustomsProcessing customsProcessing = crmdto.getCustomsProcessing().build();
        crm.setCustomsProcessing(customsProcessingRepository.save(customsProcessing));
        log.info("Проверка перевозчика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getCarrier().getOrganizationName(),
                crmdto.getCarrier().getTaxId(), crmdto.getCarrier().getRegistrationCode(), RoleIndividuals.CARRIER).isEmpty()){
            Individuals individuals = crmdto.getCarrier().build();
            individuals.setAddress(addressRepository.save(crmdto.getCarrier().getAddress().build()));
            individuals.setRoleIndividuals(RoleIndividuals.CARRIER);
            crm.setCarrier(individualsRepository.save(individuals));
        }
        else{
            crm.getCarrier().setAddress(addressRepository.save(crmdto.getCarrier().getAddress().build()));
            crm.setCarrier(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getCarrier().getOrganizationName(),
                    crmdto.getCarrier().getTaxId(), crmdto.getCarrier().getRegistrationCode(), RoleIndividuals.CARRIER).orElse(null));
        }
        log.info("Проверка последущего перевозчика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSubsequentCarrier().getOrganizationName(),
                crmdto.getSubsequentCarrier().getTaxId(), crmdto.getSubsequentCarrier().getRegistrationCode(), RoleIndividuals.SUBSEQUENTCARRIER).isEmpty()){
            Individuals individuals = crmdto.getSender().build();
            individuals.setRoleIndividuals(RoleIndividuals.SUBSEQUENTCARRIER);
            individuals.setAddress(addressRepository.save(crmdto.getSubsequentCarrier().getAddress().build()));
            crm.setSubsequentCarrier(individualsRepository.save(individuals));
        }
        else{
            crm.getSubsequentCarrier().setAddress(addressRepository.save(crmdto.getSubsequentCarrier().getAddress().build()));
            crm.setSubsequentCarrier(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSubsequentCarrier().getOrganizationName(),
                    crmdto.getSubsequentCarrier().getTaxId(), crmdto.getSubsequentCarrier().getRegistrationCode(), RoleIndividuals.SUBSEQUENTCARRIER).orElse(null));
        }
        crm.setAccount(account);
        crmRepository.save(crm);

    }

    public CRMDTO getCRM(String login) {
        CRMDTO crmdto = new CRMDTO();
        Individuals supplier = individualsService.findRegistrationSupplier(login);
        crmdto.setSender(supplier.buildDTO());
        return crmdto;
    }
}
