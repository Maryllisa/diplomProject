package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.declaration.ProductLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationDTO {
    private String customEDCode;
    private String directionOfMovement;
    private String procedureCode;

    private IndividualsDTO senderDTO;

    private String numbList;
    private String colList;

    private String colSpec;
    private String colListSpec;

    private int colProd;
    private String colMest;

    private String osobenOfDeclar;

    private IndividualsDTO recipientDTO;

    private IndividualsDTO otvetstvenoeFace;

    private String tradingCountry;

    private double tamPrice;

    private IndividualsDTO declarator;

    private String countryCodeDeparture;
    private String countryNameDeparture;

    private String countryCodeOrigin;
    private String countryNameOrigin;

    private String countryCodeDestination;
    private String countryNameDestination;

    private String vehicleCount;
    private String vehicleNumbers;

    private String conteiner;

    private String codeYP;
    private String nameYP;

    private String vehicleCountOnBorder;
    private String vehicleNumbersOnBorder;

    private String currencyCode;
    private String invoiceAmount;
    private boolean freeDeliveryCheckbox;


    private CurrencyRateDTO currencyRateDTO;

    private String transactionCharacterCode;
    private String foreignTradeFeatureCode;

    private String borderTransportTypeCode;

    private String domesticTransportTypeCode;

    private double grossWeight;

    private double netWeight;

    private String customsCode;
    private String largeTextArea;

    private ProductLocation productLocationDTO;

    private String productDescription;

    private List<ProductDTO> productDTOS;

    public DeclarationTD buildWithoutEntity(){
        DeclarationTD declarationTDForDB = new DeclarationTD();
        declarationTDForDB.setDeclarationNumber(customEDCode+"/"+ directionOfMovement+"/"+ procedureCode);
        declarationTDForDB.setFormGr3(numbList +"/"+colList);
        declarationTDForDB.setSpecification(colSpec +"/" +colListSpec);
        declarationTDForDB.setColProducts(colProd);
        declarationTDForDB.setAllPlace(colMest);
        declarationTDForDB.setDeclarationDetails(osobenOfDeclar);
        declarationTDForDB.setTorgCountry(tradingCountry);
        declarationTDForDB.setCost(tamPrice);
        declarationTDForDB.setCodeSenderCountry(countryCodeDeparture);
        declarationTDForDB.setNameSenderCountry(countryNameDeparture);
        declarationTDForDB.setCodeOriginCountry(countryCodeOrigin);
        declarationTDForDB.setNameOriginCountry(countryNameOrigin);
        declarationTDForDB.setCodeRecipientCountry(countryCodeDestination);
        declarationTDForDB.setNameRecipientCountry(countryNameDestination);
        declarationTDForDB.setIdentification(vehicleCount);
        declarationTDForDB.setVehicleRegistrationCountry(vehicleNumbers);
        declarationTDForDB.setCodeContiner(conteiner);
        declarationTDForDB.setUpCode(codeYP);
        declarationTDForDB.setConditionsOfDeliveryName(nameYP);
        declarationTDForDB.setNumberOfVehicles(vehicleCountOnBorder);
        declarationTDForDB.setVehicleNumbers(vehicleNumbersOnBorder);
        declarationTDForDB.setDealCode(transactionCharacterCode);
        declarationTDForDB.setSpecialEconomicDealCode(foreignTradeFeatureCode);

        declarationTDForDB.setCodeTransport(borderTransportTypeCode);
        declarationTDForDB.setCodeTransportInCountry(domesticTransportTypeCode);
        declarationTDForDB.setTotalGrossWeight(grossWeight);
        declarationTDForDB.setTotalNetWeight(netWeight);
        declarationTDForDB.setCustomsBorderCode(customsCode);
        declarationTDForDB.setCustomsBorderDescription(largeTextArea);
        declarationTDForDB.setProductDescription(productDescription);

        return declarationTDForDB;

    }

}
