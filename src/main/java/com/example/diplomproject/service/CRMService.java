package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            Individuals individuals = crm.getSender();
            individuals.setAddress(addressRepository.save(crm.getSender().getAddress()));
            individuals.setAccount(account);
            crm.setSender(individualsRepository.save(individuals));
        }
        else{
            crm.getSender().setAddress(addressRepository.save(crm.getSender().getAddress()));
            crm.setSender(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSender().getOrganizationName(),
                    crmdto.getSender().getTaxId(), crmdto.getSender().getRegistrationCode(), RoleIndividuals.SUPPLIER).orElse(null));
        }
        log.info("Проверка получателя");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getResipient().getOrganizationName(),
                crmdto.getResipient().getTaxId(), crmdto.getResipient().getRegistrationCode(), RoleIndividuals.RECIPIENT).isEmpty()){
            Individuals individuals = crm.getResipient();
            individuals.setAddress(addressRepository.save(crm.getResipient().getAddress()));
            crm.setResipient(individualsRepository.save(individuals));
        }
        else{
            crm.getResipient().setAddress(addressRepository.save(crm.getResipient().getAddress()));
            crm.setResipient(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getResipient().getOrganizationName(),
                    crmdto.getResipient().getTaxId(), crmdto.getResipient().getRegistrationCode(), RoleIndividuals.RECIPIENT).orElse(null));
        }
        CustomsProcessing customsProcessing = crm.getCustomsProcessing();
        crm.setCustomsProcessing(customsProcessingRepository.save(customsProcessing));
        log.info("Проверка перевозчика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getCarrier().getOrganizationName(),
                crmdto.getCarrier().getTaxId(), crmdto.getCarrier().getRegistrationCode(), RoleIndividuals.CARRIER).isEmpty()){
            Individuals individuals = crm.getCarrier();
            individuals.setAddress(addressRepository.save(crm.getCarrier().getAddress()));
            crm.setCarrier(individualsRepository.save(individuals));
        }
        else{
            crm.getCarrier().setAddress(addressRepository.save(crm.getCarrier().getAddress()));
            crm.setCarrier(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getCarrier().getOrganizationName(),
                    crmdto.getCarrier().getTaxId(), crmdto.getCarrier().getRegistrationCode(), RoleIndividuals.CARRIER).orElse(null));
        }
        log.info("Проверка последущего перевозчика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSubsequentCarrier().getOrganizationName(),
                crmdto.getSubsequentCarrier().getTaxId(), crmdto.getSubsequentCarrier().getRegistrationCode(), RoleIndividuals.SUBSEQUENTCARRIER).isEmpty()){
            Individuals individuals = crm.getSubsequentCarrier();
            individuals.setRoleIndividuals(RoleIndividuals.SUBSEQUENTCARRIER);
            individuals.setAddress(addressRepository.save(crm.getSubsequentCarrier().getAddress()));
            crm.setSubsequentCarrier(individualsRepository.save(individuals));
        }
        else{
            crm.getSubsequentCarrier().setAddress(addressRepository.save(crm.getSubsequentCarrier().getAddress()));
            crm.setSubsequentCarrier(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSubsequentCarrier().getOrganizationName(),
                    crmdto.getSubsequentCarrier().getTaxId(), crmdto.getSubsequentCarrier().getRegistrationCode(), RoleIndividuals.SUBSEQUENTCARRIER).orElse(null));
        }
        crm.setAccount(account);
        crmRepository.save(crm);

    }

    public CRMDTO getCRM(String login) {
        CRMDTO crmdto = new CRMDTO();
        Individuals supplier = individualsService.findRegistrationSupplier(login);
        crmdto.setSender(supplier.build(RoleIndividuals.CARRIER));
        return crmdto;
    }

    public List<CRM> findAllByAccount(String name) {
        Account account = userRepository.findByLogin(name);
        return crmRepository.findAllByAccount(account);
    }
}
