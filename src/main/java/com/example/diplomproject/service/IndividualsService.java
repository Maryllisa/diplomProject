package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
import com.example.diplomproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Individuals> getSuppliers(String login) {
         return individualsRepository.findByAccount(accountRepository.findByLogin(login));
    }
    public IndividualsDTO findById(Long id){
        Individuals individuals =individualsRepository.findById(id).orElse(null);
        if (individuals!=null){
            return individuals.build(RoleIndividuals.SUPPLIER);
        }
        return new IndividualsDTO();
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
        individuals.setAccount(accountRepository.findByLogin(login));
        individuals.setAddress(addressRepository.save(individualsDTO.getAddress().build()));
        individualsRepository.save(individuals);
    }

    public Map<String, String> check(BindingResult result, DeclarationDTO declarationDTO, String check) {
        Map<String,String> resultMap = new HashMap<>();
        if (check.contains("senderDTO")) check = check.substring(10);
        else if (check.contains("recipientDTO")) check = check.substring(13);
        else if (check.contains("otvetstvenoeFace")) check = check.substring(17);
        else check = check.substring(11);
        switch (check){
            case "organizationName":{
                resultMap.put("organizationName", "Ошибка при заполнении графы название организации");
                break;
            }
            case "legalAddress":{
                resultMap.put("legalAddress", "Ошибка при заполнении графы адреса");
                break;
            }
            case "phone":{
                resultMap.put("phone", "Ошибка при заполнении графы телефона");
                break;
            }
            case "bankCode":{
                resultMap.put("bankCode", "Ошибка при заполнении графы код банка");
                break;
            }
            case "bankName":{
                resultMap.put("bankName", "Ошибка при заполнении графы название банка");
                break;
            }
            case "address.city":{
                resultMap.put("city", "Ошибка при заполнении графы страны");
                break;
            }
            case "address.postalCode":{
                resultMap.put("postalCode", "Ошибка при заполнении графы почтового индекса");
                break;
            }
            case "address.region":{
                resultMap.put("region", "Ошибка при заполнении графы название региона");
                break;
            }
            case "address.settlement":{
                resultMap.put("settlement", "Ошибка при заполнении графы название области");
                break;
            }
            case "address.build":{
                resultMap.put("build", "Ошибка при заполнении графы название адреса");
                break;
            }
            case "address.ogrnNumber":{
                resultMap.put("ogrnNumber", "Ошибка при заполнении графы ОГРН");
                break;
            }
            case "taxId":{
                resultMap.put("taxId", "Ошибка при заполнении графы ОНП");
                break;
            }
            case "registrationCode":{
                resultMap.put("registrationCode", "Ошибка при заполнении графы регистрационного кода");
                break;
            }

        }
        return resultMap;
    }

    public Map<String, String> check(BindingResult result, CRMDTO crmdto, String check) {
        Map<String,String> resultMap = new HashMap<>();
        if (check.contains("sender")) check = check.substring(7);
        else if (check.contains("resipient")) check = check.substring(10);
        else if (check.contains("carrier")) check = check.substring(8);
        else check = check.substring(18);
        switch (check){
            case "organizationName":{
                resultMap.put("organizationName", "Ошибка при заполнении графы название организации");
                break;
            }
            case "legalAddress":{
                resultMap.put("legalAddress", "Ошибка при заполнении графы адреса");
                break;
            }
            case "phone":{
                resultMap.put("phone", "Ошибка при заполнении графы телефона");
                break;
            }
            case "bankCode":{
                resultMap.put("bankCode", "Ошибка при заполнении графы код банка");
                break;
            }
            case "bankName":{
                resultMap.put("bankName", "Ошибка при заполнении графы название банка");
                break;
            }
            case "address.city":{
                resultMap.put("city", "Ошибка при заполнении графы страны");
                break;
            }
            case "address.postalCode":{
                resultMap.put("postalCode", "Ошибка при заполнении графы почтового индекса");
                break;
            }
            case "address.region":{
                resultMap.put("region", "Ошибка при заполнении графы название региона");
                break;
            }
            case "address.settlement":{
                resultMap.put("settlement", "Ошибка при заполнении графы название области");
                break;
            }
            case "address.build":{
                resultMap.put("build", "Ошибка при заполнении графы название адреса");
                break;
            }
            case "address.ogrnNumber":{
                resultMap.put("ogrnNumber", "Ошибка при заполнении графы ОГРН");
                break;
            }
            case "taxId":{
                resultMap.put("taxId", "Ошибка при заполнении графы ОНП");
                break;
            }
            case "registrationCode":{
                resultMap.put("registrationCode", "Ошибка при заполнении графы регистрационного кода");
                break;
            }

        }
        return resultMap;
    }

    public Map<String, String> check(BindingResult result, IndividualsDTO individualsDTO) {
        Map<String,String> resultMap = new HashMap<>();
        result.getFieldErrors().forEach(x->{
            switch (x.getField()){
                case "organizationName":{
                    resultMap.put("organizationName", "Ошибка при заполнении графы название организации");
                    break;
                }
                case "legalAddress":{
                    resultMap.put("legalAddress", "Ошибка при заполнении поля адреса");
                    break;
                }
                case "phone":{
                    resultMap.put("phone", "Ошибка при заполнении поля телефона");
                    break;
                }
                case "bankCode":{
                    resultMap.put("bankCode", "Ошибка при заполнении поля код банка");
                    break;
                }
                case "bankName":{
                    resultMap.put("bankName", "Ошибка при заполнении поля название банка");
                    break;
                }
                case "address.city":{
                    resultMap.put("city", "Ошибка при заполнении поля страны");
                    break;
                }
                case "address.postalCode":{
                    resultMap.put("postalCode", "Ошибка при заполнении поля почтового индекса");
                    break;
                }
                case "address.region":{
                    resultMap.put("region", "Ошибка при заполнении поля название региона");
                    break;
                }
                case "address.settlement":{
                    resultMap.put("settlement", "Ошибка при заполнении поля название области");
                    break;
                }
                case "address.build":{
                    resultMap.put("build", "Ошибка при заполнении графы название адреса");
                    break;
                }
                case "address.ogrnNumber":{
                    resultMap.put("ogrnNumber", "Ошибка при заполнении графы ОГРН");
                    break;
                }
                case "taxId":{
                    resultMap.put("taxId", "Ошибка при заполнении графы ОНП");
                    break;
                }
                case "registrationCode":{
                    resultMap.put("registrationCode", "Ошибка при заполнении графы регистрационного кода");
                    break;
                }

            }
        });
        return resultMap;
    }
}
