package com.example.diplomproject.model.dto;

import com.example.diplomproject.config.annotation.imp.ValidDate;
import com.example.diplomproject.config.annotation.imp.ValidDateFetcher;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationForStorageDTO {
    private Long idApplication;

    private int countPositionProducts;
    @NotNull
    @ValidDateFetcher
    private Date datePost;
    @NotNull
    @ValidDate
    private Date dateZav;
    private StatusApplication statusApplication;
    @NotNull
    private Long idGoodTransportDocument;
    private GoodTransportDocument goodTransportDocument;
    @NotNull
    private Long idDeclarationTD;
    @JsonIgnore
    private DeclarationDTO declarationTD;
    @NotNull
    private Long idCRM;
    @JsonIgnore
    private CRMDTO crm;
    @NotNull
    private Long idTruck;
    @JsonIgnore
    private TruckDTO truck;

    public ApplicationForStorage build(StatusApplication statusApplication) {
        return ApplicationForStorage.builder()

                .countPositionProducts(declarationTD.getColProd())
                .datePost(datePost)
                .dateZav(dateZav)
                .statusApplication(statusApplication)
                .goodTransportDocument(goodTransportDocument)
                .declarationTD(declarationTD.build())
                .crm(crm.build())
                .truck(truck.build())
                .statusApplication(statusApplication)
                .build();
    }

    public ApplicationForStorage build(StatusApplication statusApplication,
                                       GoodTransportDocument goodTransportDocument,
                                       DeclarationTD declarationTD,
                                       CRM crm, Truck truck) {
        return ApplicationForStorage.builder()
                .countPositionProducts(declarationTD.getColProducts())
                .datePost(datePost)
                .dateZav(dateZav)
                .statusApplication(statusApplication)
                .goodTransportDocument(goodTransportDocument)
                .declarationTD(declarationTD)
                .crm(crm)
                .truck(truck)
                .statusApplication(statusApplication)
                .build();
    }
}
