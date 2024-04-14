package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import com.example.diplomproject.model.dto.dtoForDeclaration.ProductLocationDTO;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    private ProductLocationDTO productLocationDTO;

    private String productDescription;

    private List<ProductDTO> productDTOS;
    public DeclarationTD build(){
        return DeclarationTD.builder()
                .declarationNumber(customEDCode + "/"
                        + directionOfMovement +"/"
                        +procedureCode)
                .individuals(senderDTO.build(RoleIndividuals.SUPPLIER))
                .formGr3(numbList+ "/" +colList)
                .specification(colSpec +"/"+ colListSpec)
                .colProducts(colProd)
                .allPlace(colMest)
                .declarationDetails(osobenOfDeclar)
                .recipientAddress(recipientDTO.build(RoleIndividuals.RECIPIENT))
                .financialRegulator(otvetstvenoeFace.build(RoleIndividuals.FINYREG))
                .torgCountry(tradingCountry)
                .cost(tamPrice)
                .declarant(declarator.build(RoleIndividuals.DECLARANT))
                .codeSenderCountry(countryCodeDeparture)
                .nameSenderCountry(countryNameDeparture)
                .codeOriginCountry(countryCodeOrigin)
                .nameOriginCountry(countryNameOrigin)
                .codeRecipientCountry(countryCodeDestination)
                .nameRecipientCountry(countryNameDestination)
                .identification(vehicleCount)
                .vehicleRegistrationCountry(vehicleNumbers)
                .codeContiner(conteiner)
                .upCode(codeYP)
                .conditionsOfDeliveryName(nameYP)
                .numberOfVehicles(vehicleCountOnBorder)
                .vehicleNumbers(vehicleNumbersOnBorder)
                .currency(currencyCode)
                .accountTotalAmount(invoiceAmount)
                .currencyRate(currencyRateDTO.build())
                .dealCode(transactionCharacterCode)
                .specialEconomicDealCode(foreignTradeFeatureCode)
                .codeTransport(borderTransportTypeCode)
                .codeTransportInCountry(domesticTransportTypeCode)
                .totalGrossWeight(grossWeight)
                .totalNetWeight(netWeight)
                .customsBorderCode(customsCode)
                .customsBorderDescription(largeTextArea)
                .productLocation(productLocationDTO.build())
                .productDescription(productDescription)
                .build();
    }



}
