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
    private GoodTransportDocument goodTransportDocument;
    private DeclarationTD declarationTD;
    private CRM crm;
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
}
