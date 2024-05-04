package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeclarationDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductLocationService {
    public Map<String, String> check(BindingResult result, DeclarationDTO declarationDTO, String check) {
        Map<String,String> resultMap = new HashMap<>();
        check = check.substring(19);
        switch (check){
            case "uzoRegistry":{
                resultMap.put("uzoRegistry", "Ошибка при заполнении графы местонахождение товара (Регистр УЗО)");
                break;
            }
            case "type":{
                resultMap.put("type", "Ошибка при заполнении графы местонахождение товара (тип)");
                break;
            }
            case "quantity":{
                resultMap.put("quantity", "Ошибка при заполнении графы местонахождение товара (1/2/3)");
                break;
            }
            case "documentNumber":{
                resultMap.put("documentNumber", "Ошибка при заполнении графы местонахождение товара (Номер документа)");
                break;
            }
            case "date":{
                resultMap.put("date", "Ошибка при заполнении графы местонахождение товара (Дата)");
                break;
            }
            case "ztkNumber":{
                resultMap.put("ztkNumber", "Ошибка при заполнении графы местонахождение товара (Вид транспорта)");
                break;
            }
            case "transportType":{
                resultMap.put("transportType", "Ошибка при заполнении графы местонахождение товара (Вид транспорта)");
                break;
            }
            case "vehicleNumber":{
                resultMap.put("vehicleNumber", "Ошибка при заполнении графы местонахождение товара (Номер транспортного средства)");
                break;
            }
            case "stationOrPort":{
                resultMap.put("stationOrPort", "Ошибка при заполнении графы местонахождение товара (Ж/д станция / порт)");
                break;
            }
            case "country":{
                resultMap.put("country", "Ошибка при заполнении графы местонахождение товара (Cтрана)");
                break;
            }
            case "postalCode":{
                resultMap.put("postalCode", "Ошибка при заполнении графы местонахождение товара (Почтовый код)");
                break;
            }
            case "regionOrDistrict":{
                resultMap.put("regionOrDistrict", "Ошибка при заполнении графы местонахождение товара (Область, район)");
                break;
            }
            case "locality":{
                resultMap.put("locality", "Ошибка при заполнении графы местонахождение товара (Населённый пункт)");
                break;
            }
            case "houseNumber":{
                resultMap.put("houseNumber", "Ошибка при заполнении графы местонахождение товара (Дом)");
                break;
            }

        }
        return resultMap;
    }
}
