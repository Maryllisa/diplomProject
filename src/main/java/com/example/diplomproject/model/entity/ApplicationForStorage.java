package com.example.diplomproject.model.entity;

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


}
