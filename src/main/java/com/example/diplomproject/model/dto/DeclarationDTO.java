package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import com.example.diplomproject.model.dto.dtoForDeclaration.ProductLocationDTO;
import com.example.diplomproject.model.entity.Supplier;
import com.example.diplomproject.model.entity.declaration.CurrencyRate;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.declaration.ProductLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeclarationDTO {
    private String customEDCode;
    private String directionOfMovement;
    private String procedureCode;

    private SupplierDTO senderDTO;

    private String numbList;
    private String colList;

    private String colSpec;
    private String colListSpec;

    private int colProd;
    private String colMest;

    private String osobenOfDeclar;

    private SupplierDTO recipientDTO;

    private SupplierDTO otvetstvenoeFace;

    private String tradingCountry;

    private double tamPrice;

    private SupplierDTO declarator;

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
        DeclarationTD declarationTD = new DeclarationTD();
        declarationTD.setFormGr3(numbList + " "+ colList);
        declarationTD.setSpecification(colSpec+"/"+colListSpec);
        declarationTD.setColProducts(colProd);
        declarationTD.setAllPlace(colMest);
        declarationTD.setDeclarationDetails(osobenOfDeclar);
        declarationTD.setRecipientCompany(recipientDTO.getOrganizationName());
        declarationTD.setRecipientAddress(recipientDTO.getAddress().build());
        // тут про ответсвенное лицо, но я не придумала зачем мне эта инфа, поэтому куда ее засунуть соответственно
        declarationTD.setTorgCountry(tradingCountry);
        declarationTD.setCost(tamPrice);
        declarationTD.setDeclarant(declarator.getAddress().build());
        declarationTD.setCodeSenderCountry(countryCodeDeparture);
        declarationTD.setNameSenderCountry(countryNameDeparture);
        declarationTD.setCodeOriginCountry(countryCodeOrigin);
        declarationTD.setNameOriginCountry(countryNameOrigin);
        declarationTD.setCodeRecipientCountry(countryCodeDestination);
        declarationTD.setNameRecipientCountry(countryNameDestination);
        declarationTD.setIdentification(vehicleCount);
        declarationTD.setVehicleRegistrationCountry(vehicleNumbers);
        declarationTD.setCodeContiner(conteiner);
        declarationTD.setUpCode(codeYP);
        declarationTD.setConditionsOfDeliveryName(nameYP);
        declarationTD.setNumberOfVehicles(vehicleCountOnBorder);
        if (freeDeliveryCheckbox) // условие надо прописать, хотя мне до сих пор не понятно зачем оно
            declarationTD.setCurrency(currencyCode);
        declarationTD.setAccountTotalAmount(invoiceAmount);
        declarationTD.setCurrencyRate(currencyRateDTO.build());
        declarationTD.setDealCode(transactionCharacterCode);
        declarationTD.setSpecialEconomicDealCode(foreignTradeFeatureCode);

        declarationTD.setCodeTransport(borderTransportTypeCode);
        declarationTD.setCodeTransportInCountry(domesticTransportTypeCode);
        declarationTD.setTotalGrossWeight(grossWeight);
        declarationTD.setTotalNetWeight(netWeight);
        declarationTD.setCustomsBorderCode(customsCode);
        declarationTD.setCustomsBorderDescription(largeTextArea);
        declarationTD.setProductDescription(productDescription);
        declarationTD.setProductLocation(productLocationDTO.build());
        return declarationTD;
    }

}
