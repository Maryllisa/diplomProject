package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import com.example.diplomproject.model.dto.CRMDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomsProcessingService {


    public Map<String, String> check(BindingResult result, CRMDTO crmdto, String check) {
        Map<String,String> resultMap = new HashMap<>();
        check = check.substring(18);
        switch (check){
            case "date":{
                resultMap.put("organizationName", "Ошибка при заполнении графы название организации");
                break;
            }
            case "issueDate":{
                resultMap.put("issueDate", "Ошибка при заполнении графы название организации");
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
