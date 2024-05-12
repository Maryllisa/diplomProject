package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.declaration.CurrencyRate;
import com.example.diplomproject.model.entity.declaration.ProductLocation;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.repository.*;
import com.sun.mail.imap.protocol.IMAPProtocol;
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
public class DeclarationTDService {
    private final DeclarationTDRepository declarationTDRepository;
    private final UserRepository userRepository;
    private final IndividualsRepository individualsRepository;
    private final ProductService productService;
    private final AddressRepository addressRepository;
    private final CurrencyRateRepository currencyRateRepository;
    private final IndividualsService individualsService;
    private final ProductLocationRepository productLocationRepository;
    private final CurrencyRateService currencyRateService;
    private final ProductLocationService prductionLocationService;

    public DeclarationDTO geFormForNewDeclaration(String login) { // переписать логику
        DeclarationDTO declarationDTO = new DeclarationDTO();
        Account account = userRepository.findByLogin(login);
        Individuals supplier = individualsService.findRegistrationSupplier(login);
        declarationDTO.setSenderDTO(supplier.build(RoleIndividuals.CARRIER));
        return declarationDTO;

    }
    public Map<String, String> checkNewDeclaration(BindingResult result,
                                                   DeclarationDTO declarationDTO) {
        Map<String, String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(error ->{
            switch (error.getField()){
                case "customEDCode":{
                    descriptionError.put("customEDCode", "Ошибка при заполнении графы 1, должно быть ЭК или ИМ");
                    break;
                }
                case "directionOfMovement":{
                    descriptionError.put("directionOfMovement", "Ошибка при заполнении графы 1, некорректный номер");
                    break;
                }
                case "procedureCode":{
                    descriptionError.put("procedureCode", "Ошибка при заполнении графы 1, поле должно быть ЭД или не заполненно");
                    break;
                }
                case "numbList":{
                    descriptionError.put("numbList", "Ошибка при заполнении графы формы, должны быть цифры");
                    break;
                }
                case "colList":{
                    descriptionError.put("colList", "Ошибка при заполнении графы формы, должны быть цифры");
                    break;
                }
                case "colSpec":{
                    descriptionError.put("colSpec", "Ошибка при заполнении графы спецификации");
                    break;
                }
                case "colListSpec":{
                    descriptionError.put("colListSpec", "Ошибка при заполнении графы спецификации");
                    break;
                }
                case "colProd":{
                    descriptionError.put("colProd", "Ошибка при заполнении графы всего товаров");
                    break;
                }
                case "colMest":{
                    descriptionError.put("colMest", "Ошибка при заполнении графы всего мест");
                    break;
                }
                case "osobenOfDeclar":{
                    descriptionError.put("osobenOfDeclar", "Ошибка при заполнении графы особенности декларирования");
                    break;
                }
                case "tradingCountry":{
                    descriptionError.put("tradingCountry", "Ошибка при заполнении графы торгующая строна");
                    break;
                }
                case "tamPrice":{
                    descriptionError.put("tamPrice", "Ошибка при заполнении графы общая таможенная стоимость");
                    break;
                }
                case "countryCodeDeparture":{
                    descriptionError.put("countryCodeDeparture", "Ошибка при заполнении графы код страны отправления");
                    break;
                }
                case "countryNameDeparture":{
                    descriptionError.put("countryNameDeparture", "Ошибка при заполнении графы наименование страны отправления");
                    break;
                }
                case "countryCodeOrigin":{
                    descriptionError.put("countryCodeOrigin", "Ошибка при заполнении графы код страны происхождения");
                    break;
                }
                case "countryNameOrigin":{
                    descriptionError.put("countryNameOrigin", "Ошибка при заполнении графы наименование страны происхождения");
                    break;
                }
                case "countryCodeDestination":{
                    descriptionError.put("countryCodeDestination", "Ошибка при заполнении графы код страны назначения");
                    break;
                }
                case "countryNameDestination":{
                    descriptionError.put("countryNameDestination", "Ошибка при заполнении графы наименование страны назначения");
                    break;
                }
                case "vehicleCount":{
                    descriptionError.put("vehicleCount", "Ошибка при заполнении графы 19");
                    break;
                }
                case "vehicleNumbers":{
                    descriptionError.put("vehicleNumbers", "Ошибка при заполнении графы 20");
                    break;
                }
                case "conteiner":{
                    descriptionError.put("conteiner", "Ошибка при заполнении графы контейнер");
                    break;
                }
                case "codeYP":{
                    descriptionError.put("codeYP", "Ошибка при заполнении графы условия поставки (код)");
                    break;
                }
                case "nameYP":{
                    descriptionError.put("nameYP", "Ошибка при заполнении графы условия поставки (наименование)");
                    break;
                }
                case "vehicleCountOnBorder":{
                    descriptionError.put("vehicleCountOnBorder", "Ошибка при заполнении графы валюты и общей суммы по счету");
                    break;
                }
                case "vehicleNumbersOnBorder":{
                    descriptionError.put("vehicleNumbersOnBorder", "Ошибка при заполнении графы валюты и общей суммы по счету ");
                    break;
                }
                case "currencyCode":{
                    descriptionError.put("currencyCode", "Ошибка при заполнении графы валюты и общей суммы по счету");
                    break;
                }
                case "invoiceAmount":{
                    descriptionError.put("invoiceAmount", "Ошибка при заполнении графы валюты и общей суммы по счету");
                    break;
                }
                case "transactionCharacterCode":{
                    descriptionError.put("transactionCharacterCode", "Ошибка при заполнении графы 24 (код)");
                    break;
                }
                case "foreignTradeFeatureCode":{
                    descriptionError.put("foreignTradeFeatureCode", "Ошибка при заполнении графы 24");
                    break;
                }
                case "borderTransportTypeCode":{
                    descriptionError.put("borderTransportTypeCode", "Ошибка при заполнении графы 25");
                    break;
                }
                case "domesticTransportTypeCode":{
                    descriptionError.put("domesticTransportTypeCode", "Ошибка при заполнении графы 26");
                    break;
                }
                case "grossWeight":{
                    descriptionError.put("grossWeight", "Ошибка при заполнении графы вес брутта");
                    break;
                }
                case "netWeight":{
                    descriptionError.put("netWeight", "Ошибка при заполнении графы вес нетта");
                    break;
                }
                case "customsCode":{
                    descriptionError.put("customsCode", "Ошибка при заполнении графы таможня на границе (код)");
                    break;
                }
                case "largeTextArea":{
                    descriptionError.put("largeTextArea", "Ошибка при заполнении графы таможня на границе");
                    break;
                }
                case "productDescription":{
                    descriptionError.put("productDescription", "Ошибка при заполнении графы описания товара ");
                    break;
                }
                default: {
                    String check = error.getField();
                    if (check.contains("senderDTO")
                            || check.contains("recipientDTO")
                            || check.contains("otvetstvenoeFace")
                            || check.contains("declarator"))
                        descriptionError.putAll(individualsService.check(result, declarationDTO, check));
                    else if (check.contains("currencyRateDTO"))
                        descriptionError.putAll(currencyRateService.check(result, declarationDTO, check));
                    else descriptionError.putAll(prductionLocationService.check(result, declarationDTO, check));
                }

            }
        });
        return descriptionError;
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

    public List<DeclarationDTO> findAllByAccount(String name) {
        Account account = userRepository.findByLogin(name);
        List<DeclarationDTO> declarationDTOList = new ArrayList<>();
        declarationTDRepository.findAllByAccount(account).forEach(x->{
            declarationDTOList.add(x.build());
        });
        return declarationDTOList;
    }

    public List<Individuals> getSupplier(String login) {
        return individualsRepository.findByAccount(userRepository.findByLogin(login));
    }

    public List<DeclarationDTO> getAllDeclaration() {
        List<DeclarationTD> declarationTDList = declarationTDRepository.findAll();
        List<DeclarationDTO> declarationDTOList = new ArrayList<>();
        declarationTDList.forEach(x->{
            declarationDTOList.add(x.build());
        });
        return declarationDTOList;
    }

    public List<Product> findAllByDeclarationId(Long declarationId) {
        return productService.getAllProductByDeclaration(declarationId);
    }

    public DeclarationTD getById(Long id) {
        return declarationTDRepository.getById(id);
    }
}
