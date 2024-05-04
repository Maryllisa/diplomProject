package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
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
    private final CustomsProcessingService customPricessingService;

    public Map<String, String> checkNewCRM(BindingResult result, CRMDTO crmdto) {
        Map<String, String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            switch (error.getField()) {
                case "countryWH": {
                    descriptionError.put("countryWH", "Ошибка при заполнении графы место разгрузки (страна)");
                    break;
                }
                case "cityWH": {
                    descriptionError.put("cityWH", "Ошибка при заполнении графы место разгрузки (город)");
                    break;
                }
                case "streetWH": {
                    descriptionError.put("streetWH", "Ошибка при заполнении графы место разгрузки (улица)");
                    break;
                }
                case "houseNumberWH": {
                    descriptionError.put("houseNumberWH", "Ошибка при заполнении графы место разгрузки (дом)");
                    break;
                }
                case "loadingCountry": {
                    descriptionError.put("loadingCountry", "Ошибка при заполнении графы место и дата погрузки (страна)");
                    break;
                }
                case "loadingStreet": {
                    descriptionError.put("loadingStreet", "Ошибка при заполнении графы место и дата погрузки (улица)");
                    break;
                }
                case "loadingDate": {
                    descriptionError.put("loadingDate", "Ошибка при заполнении графы место и дата погрузки (дата)");
                    break;
                }
                case "loadingCity": {
                    descriptionError.put("cmrFilledDate", "Ошибка при заполнении графы место и дата погрузки (город)");
                    break;
                }
                case "loadingHouseNumber": {
                    descriptionError.put("loadingHouseNumber", "Ошибка при заполнении графы место и дата погрузки (дом)");
                    break;
                }
                case "invoiceDocument": {
                    descriptionError.put("invoiceDocument", "Ошибка при заполнении графы прилогаемые документы (Счет-фактура (invoice))");
                    break;
                }
                case "shippingSpecificationDocument": {
                    descriptionError.put("shippingSpecificationDocument", "Ошибка при заполнении графы прилогаемые документы (Отгрузочная спецификация)");
                    break;
                }
                case "qualityCertificateDocument": {
                    descriptionError.put("qualityCertificateDocument", "Ошибка при заполнении графы прилогаемые документы (Сертификат качества)");
                    break;
                }
                case "veterinaryCertificateDocument": {
                    descriptionError.put("veterinaryCertificateDocument", "Ошибка при заполнении графы прилогаемые документы (Ветеринарный сертификат)");
                    break;
                }
                case "quarantineCertificateDocument": {
                    descriptionError.put("countryCodeDeparture", "Ошибка при заполнении графы прилогаемые документы (Карантинный сертификат)");
                    break;
                }
                case "certificateOfOriginDocument": {
                    descriptionError.put("certificateOfOriginDocument", "Ошибка при заполнении графы прилогаемые документы (Сертификат о происхождении)");
                    break;
                }
                case "loadingCertificateDocument": {
                    descriptionError.put("loadingCertificateDocument", "Ошибка при заполнении графы прилогаемые документы (Акт загрузки)");
                    break;
                }
                case "cargoQuantity": {
                    descriptionError.put("cargoQuantity", "Ошибка при заполнении графы 6-9 (Количество грузовых мест)");
                    break;
                }
                case "cargoName": {
                    descriptionError.put("cargoName", "Ошибка при заполнении графы 6-9 (Наименование груза)");
                    break;
                }
                case "nackagingType": {
                    descriptionError.put("nackagingType", "Ошибка при заполнении графы 6-9 (Род упаковки)");
                    break;
                }
                case "numbers": {
                    descriptionError.put("numbers", "Ошибка при заполнении графы 6-9 (Номера)");
                    break;
                }
                case "statistikCode": {
                    descriptionError.put("statistikCode", "Ошибка при заполнении графы статистический код");
                    break;
                }
                case "grossWeight": {
                    descriptionError.put("grossWeight", "Ошибка при заполнении графы суммарный вес брутто");
                    break;
                }
                case "volume": {
                    descriptionError.put("volume", "Ошибка при заполнении графы объем");
                    break;
                }
                case "returnCountry": {
                    descriptionError.put("returnCountry", "Ошибка при заполнении графы возврат (страна)");
                    break;
                }
                case "returnCity": {
                    descriptionError.put("returnCity", "Ошибка при заполнении графы возврат (город) ");
                    break;
                }
                case "returnStreet": {
                    descriptionError.put("returnStreet", "Ошибка при заполнении графы возврат (улица)");
                    break;
                }
                case "returnHouse": {
                    descriptionError.put("returnHouse", "Ошибка при заполнении графы возврт (дом)");
                    break;
                }
                case "paymentTerms": {
                    descriptionError.put("paymentTerms", "Ошибка при заполнении графы условия оплаты");
                    break;
                }
                case "cargoReceivedDate": {
                    descriptionError.put("cargoReceivedDate", "Ошибка при заполнении графы дата получения груза");
                    break;
                }
                case "tractorRegistrationNumber": {
                    descriptionError.put("tractorRegistrationNumber", "Ошибка при заполнении графы регистрационный номер тягача");
                    break;
                }
                case "cmrFilledDate": {
                    descriptionError.put("cmrFilledDate", "Ошибка при заполнении графы дата заполнения CRM");
                    break;
                }
                case "trailerRegistrationNumber": {
                    descriptionError.put("trailerRegistrationNumber", "Ошибка при заполнении графы регистрационный номер прицепа");
                    break;
                }
                default: {
                    String check = error.getField();
                    if (check.contains("carrier")
                            || check.contains("subsequentCarrier")
                            || check.contains("sender")
                            || check.contains("resipient"))
                        descriptionError.putAll(individualsService.check(result, crmdto, check));
                    else descriptionError.putAll(customPricessingService.check(result, crmdto, check));
                }

            }
        });
        return descriptionError;
    }

    public void addNewCRM(CRMDTO crmdto, String login) {
        CRM crm = crmdto.build();
        Account account = userRepository.findByLogin(login);
        log.info("Проверка отправителя/поставщика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSender().getOrganizationName(),
                crmdto.getSender().getTaxId(), crmdto.getSender().getRegistrationCode(), RoleIndividuals.SUPPLIER).isEmpty()) {
            Individuals individuals = crm.getSender();
            individuals.setAddress(addressRepository.save(crm.getSender().getAddress()));
            individuals.setAccount(account);
            crm.setSender(individualsRepository.save(individuals));
        } else {
            crm.getSender().setAddress(addressRepository.save(crm.getSender().getAddress()));
            crm.setSender(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSender().getOrganizationName(),
                    crmdto.getSender().getTaxId(), crmdto.getSender().getRegistrationCode(), RoleIndividuals.SUPPLIER).orElse(null));
        }
        log.info("Проверка получателя");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getResipient().getOrganizationName(),
                crmdto.getResipient().getTaxId(), crmdto.getResipient().getRegistrationCode(), RoleIndividuals.RECIPIENT).isEmpty()) {
            Individuals individuals = crm.getResipient();
            individuals.setAddress(addressRepository.save(crm.getResipient().getAddress()));
            crm.setResipient(individualsRepository.save(individuals));
        } else {
            crm.getResipient().setAddress(addressRepository.save(crm.getResipient().getAddress()));
            crm.setResipient(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getResipient().getOrganizationName(),
                    crmdto.getResipient().getTaxId(), crmdto.getResipient().getRegistrationCode(), RoleIndividuals.RECIPIENT).orElse(null));
        }
        CustomsProcessing customsProcessing = crm.getCustomsProcessing();
        crm.setCustomsProcessing(customsProcessingRepository.save(customsProcessing));
        log.info("Проверка перевозчика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getCarrier().getOrganizationName(),
                crmdto.getCarrier().getTaxId(), crmdto.getCarrier().getRegistrationCode(), RoleIndividuals.CARRIER).isEmpty()) {
            Individuals individuals = crm.getCarrier();
            individuals.setAddress(addressRepository.save(crm.getCarrier().getAddress()));
            crm.setCarrier(individualsRepository.save(individuals));
        } else {
            crm.getCarrier().setAddress(addressRepository.save(crm.getCarrier().getAddress()));
            crm.setCarrier(individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getCarrier().getOrganizationName(),
                    crmdto.getCarrier().getTaxId(), crmdto.getCarrier().getRegistrationCode(), RoleIndividuals.CARRIER).orElse(null));
        }
        log.info("Проверка последущего перевозчика");
        if (individualsRepository.findByOrganizationNameAndTaxIdAndRegistrationCodeAndRoleIndividuals(crmdto.getSubsequentCarrier().getOrganizationName(),
                crmdto.getSubsequentCarrier().getTaxId(), crmdto.getSubsequentCarrier().getRegistrationCode(), RoleIndividuals.SUBSEQUENTCARRIER).isEmpty()) {
            Individuals individuals = crm.getSubsequentCarrier();
            individuals.setRoleIndividuals(RoleIndividuals.SUBSEQUENTCARRIER);
            individuals.setAddress(addressRepository.save(crm.getSubsequentCarrier().getAddress()));
            crm.setSubsequentCarrier(individualsRepository.save(individuals));
        } else {
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

    public List<CRMDTO> findAllByAccount(String name) {
        Account account = userRepository.findByLogin(name);
        List<CRMDTO> crmdtoList = new ArrayList<>();
        crmRepository.findAllByAccount(account).forEach(x -> {
            crmdtoList.add(x.build());
        });
        return crmdtoList;
    }
}
