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

    @Pattern(regexp = "^ЭД$|^$")
    private String customEDCode;
        @NotNull
    @NotEmpty
    @Pattern(regexp = "^ЭК$|^ИМ$")
    private String directionOfMovement;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^00$|^10$|^21$|^23$|" +
            "^31$|^40$|^51$|^53$|^60$|^70$|^77$|" +
            "^78$|^80$|^90$|^91$|^93$|^94$|^96$")
    private String procedureCode;

    private IndividualsDTO senderDTO;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String numbList;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colList;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colSpec;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String colListSpec;
    @Min(value = 1)
    private int colProd;
    @NotNull
    @NotEmpty
    private String osobenOfDeclar;
    private IndividualsDTO recipientDTO;
    private IndividualsDTO otvetstvenoeFace;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d{10,12}\\|\\d{9}")
    private String tradingCountry;
    private double tamPrice;

    private IndividualsDTO declarator;

    @Pattern(regexp = "[A-Z]{2}")
    private String countryCodeDeparture;
    @NotNull
    @NotEmpty
    private String countryNameDeparture;
    @Pattern(regexp = "[A-Z]{2}")
    private String countryCodeOrigin;
    @NotNull
    @NotEmpty
    private String countryNameOrigin;
    @Pattern(regexp = "[A-Z]{2}")
    private String countryCodeDestination;
    @NotNull
    @NotEmpty
    private String countryNameDestination;
    @NotNull
    @NotEmpty
    private String vehicleCount;
    @NotNull
    @NotEmpty
    private String vehicleNumbers;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^1$|^0$")
    private String conteiner;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String codeYP;
    @NotNull
    @NotEmpty
    private String nameYP;
    @NotNull
    @NotEmpty
    private String vehicleCountOnBorder;
    @NotNull
    @NotEmpty
    private String vehicleNumbersOnBorder;
    @NotNull
    @NotEmpty
    private String currencyCode;
    @NotNull
    @NotEmpty
    private String invoiceAmount;
    private boolean freeDeliveryCheckbox;
    private CurrencyRateDTO currencyRateDTO;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^0*([1-5][0-9]|60)$")
    private String transactionCharacterCode;
    @NotNull
    @NotEmpty
    private String foreignTradeFeatureCode;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^0*([1-5][0-9]|60)$")
    private String borderTransportTypeCode;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^0*([0-1][0-2])$")
    private String domesticTransportTypeCode;
    @Min(value = 0)
    @Max(value = 1000000L)
    private double grossWeight;
    @Min(value = 0)
    @Max(value = 1000000L)
    private double netWeight;
    @NotNull
    private String customsCode;
    @NotNull
    @NotEmpty
    private String largeTextArea;

    private ProductLocationDTO productLocationDTO;
    @NotNull
    @NotEmpty
    private String productDescription;

    private List<ProductDTO> productDTOS;

    public DeclarationTD build() {
        return DeclarationTD.builder()
                .idDeclaration(idDeclaration)
                .declarationNumber(customEDCode + "/"
                        + directionOfMovement + "/"
                        + procedureCode)
                .individuals(senderDTO.build(RoleIndividuals.SUPPLIER))
                .formGr3(numbList + "/" + colList)
                .specification(colSpec + "/" + colListSpec)
                .colProducts(colProd)
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
