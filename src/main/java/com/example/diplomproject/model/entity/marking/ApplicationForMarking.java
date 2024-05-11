package com.example.diplomproject.model.entity.marking;

import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ApplicationForMarking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplicationForMarking;
    @OneToOne
    private Product product;
    @Column
    @Enumerated(EnumType.STRING)
    private TypeMarking typeMarking;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusMarkingApplication statusMarkingApplication;
    @Column
    private Date date;
    @ManyToOne
    private Account account;
    @OneToOne
    private MarkingInfo markingInfo;

    public ApplicationForMarkingDTO build() {
        return ApplicationForMarkingDTO.builder()
                .idApplication(idApplicationForMarking)
                .idProduct(product.getIdProduct())
                .nameProduct(product.getNameProduct())
                .product(product.build())
                .typeMarking(typeMarking)
                .statusMarkingApplication(statusMarkingApplication)
                .date(date)
                .build();
    }
}
