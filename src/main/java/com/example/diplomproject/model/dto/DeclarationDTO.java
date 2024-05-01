package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import com.example.diplomproject.model.dto.dtoForDeclaration.ProductLocationDTO;
import com.example.diplomproject.model.entity.enumStatus.RoleIndividuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeclarationDTO {
    private Long idDeclaration;
    @NotEmpty
    @Pattern(regexp = "^ЭК$|^ИМ$")
    private String customEDCode;
    @NotEmpty
    @Pattern(regexp = "^00$|^10$|^21$|^23$|" +
            "^31$|^40$|^51$|^53$|^60$|^70$|^77$|" +
            "^78$|^80$|^90$|^91$|^93$|^94$|^96$")
    private String directionOfMovement;
    @Pattern(regexp = "^ЭД$|^$")
    private String procedureCode;

    private IndividualsDTO senderDTO;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String numbList;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colList;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colSpec;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colListSpec;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private int colProd;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colMest;
    @NotEmpty
    private String osobenOfDeclar;
    private IndividualsDTO recipientDTO;
    private IndividualsDTO otvetstvenoeFace;
    @NotEmpty
    @Pattern(regexp = "\\d{10,12}\\|\\d{9}")
    private String tradingCountry;
    @NotEmpty
    @Pattern(regexp = "\\d+(\\.\\d+)?")
    private double tamPrice;

    private IndividualsDTO declarator;

    @Pattern(regexp = "[A-Z]{2}")
    private String countryCodeDeparture;
    @NotEmpty
    private String countryNameDeparture;
    @Pattern(regexp = "[A-Z]{2}")
    private String countryCodeOrigin;
    @NotEmpty
    private String countryNameOrigin;
    @Pattern(regexp = "[A-Z]{2}")
    private String countryCodeDestination;
    @NotEmpty
    private String countryNameDestination;
    @NotEmpty
    @Pattern(regexp = "^[A-Z]{2}$")
    private String vehicleCount;
    @NotEmpty
    private String vehicleNumbers;
    @NotEmpty
    @Pattern(regexp = "^1$|^0$")
    private String conteiner;
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String codeYP;
    @NotEmpty
    private String nameYP;
    @NotEmpty
    private String vehicleCountOnBorder;
    @NotEmpty
    private String vehicleNumbersOnBorder;
    @NotEmpty
    private String currencyCode;
    @NotEmpty
    private String invoiceAmount;
    private boolean freeDeliveryCheckbox;
    @NotEmpty
    private CurrencyRateDTO currencyRateDTO;
    @NotEmpty
    @Pattern(regexp = "^0*([1-5][0-9]|60)$")
    private String transactionCharacterCode;
    @NotEmpty
    private String foreignTradeFeatureCode;
    @NotEmpty
    @Pattern(regexp = "^0*([1-5][0-9]|60)$")
    private String borderTransportTypeCode;
    @NotEmpty
    @Pattern(regexp = "^0*([0-1][0-2])$")
    private String domesticTransportTypeCode;
    @NotEmpty
    @Min(value = 0)
    @Max(value = 1000000L)
    private double grossWeight;
    @NotEmpty
    @Min(value = 0)
    @Max(value = 1000000L)
    private double netWeight;
    @NotEmpty
    private String customsCode;
    @NotEmpty
    private String largeTextArea;

    private ProductLocationDTO productLocationDTO;
    @NotEmpty
    private String productDescription;

    private List<ProductDTO> productDTOS;
    public DeclarationTD build(){
        return DeclarationTD.builder()
                .idDeclaration(idDeclaration)
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
