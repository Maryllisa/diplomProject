package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import com.example.diplomproject.model.dto.DeclarationDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyRateService {

    public Map<String, String> check(BindingResult result, DeclarationDTO declarationDTO, String check) {
        Map<String,String> resultMap = new HashMap<>();
        check = check.substring(16);
        switch (check){
            case "date":{
                resultMap.put("organizationName", "Ошибка при заполнении графы название организации");
                break;
            }
            case "currency":{
                resultMap.put("legalAddress", "Ошибка при заполнении графы адреса");
                break;
            }
            case "currencyRate":{
                resultMap.put("phone", "Ошибка при заполнении графы телефона");
                break;
            }
            case "euroRate":{
                resultMap.put("bankCode", "Ошибка при заполнении графы код банка");
                break;
            }
            case "usdRate":{
                resultMap.put("bankName", "Ошибка при заполнении графы название банка");
                break;
            }
        }
        return resultMap;
    }
}
