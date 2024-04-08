package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import com.example.diplomproject.model.dto.dtoForDeclaration.ProductLocationDTO;
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

}
