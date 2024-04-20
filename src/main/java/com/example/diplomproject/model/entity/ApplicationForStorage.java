package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.ApplicationForStorageDTO;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ApplicationForStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplication;
    @Column
    private int countPositionProducts;
    @Column
    private Date datePost;
    @Column
    private Date dateZav;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusApplication statusApplication;
    @OneToOne
    private GoodTransportDocument goodTransportDocument;
    @OneToOne
    private DeclarationTD declarationTD;
    @OneToOne
    private CRM crm;
    @OneToOne
    private Truck truck;
    @ManyToOne
    private Account account;


    public ApplicationForStorageDTO build() {
        return ApplicationForStorageDTO.builder()
                .idApplication(idApplication)
                .idDeclarationTD(declarationTD.getIdDeclaration())
                .idCRM(crm.getIdCRM())
                .idGoodTransportDocument(goodTransportDocument.getIdGoodTransportDocument())
                .idTruck(truck.getIdTruck())
                .countPositionProducts(countPositionProducts)
                .datePost(datePost)
                .dateZav(dateZav)
                .statusApplication(statusApplication)
                .goodTransportDocument(goodTransportDocument)
                .declarationTD(declarationTD.build())
                .crm(crm.build())
                .truck(truck.build())
                .build();
    }
}
