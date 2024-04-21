package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationForStorageDTO {
    private Long idApplication;

    private int countPositionProducts;
    private Date datePost;
    private Date dateZav;
    private StatusApplication statusApplication;
    private Long idGoodTransportDocument;
    private GoodTransportDocument goodTransportDocument;
    private Long idDeclarationTD;
    private DeclarationDTO declarationTD;
    private Long idCRM;
    private CRMDTO crm;
    private Long idTruck;
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
