package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationForStorageDTO {

    private int countPositionProducts;
    private Date datePost;
    private Date dateZav;
    private StatusApplication statusApplication;
    private Long idGoodTransportDocument;
    private GoodTransportDocument goodTransportDocument;
    private Long idDeclarationTD;
    private DeclarationTD declarationTD;
    private Long idCRM;
    private CRM crm;
    private Long idTruck;
    private Truck truck;

    public ApplicationForStorage build(StatusApplication statusApplication) {
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
