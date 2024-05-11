package com.example.diplomproject.model.dto.marking;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.model.entity.marking.TypeMarking;
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
public class ApplicationForMarkingDTO {

    private Long idProduct;
    private Long idApplication;
    private String nameProduct;
    private ProductDTO product;

    private TypeMarking typeMarking;

    private StatusMarkingApplication statusMarkingApplication;
    private Date date;

    public ApplicationForMarking build(StatusMarkingApplication status) {
        return ApplicationForMarking.builder()
                .product(product.build())
                .typeMarking(typeMarking)
                .statusMarkingApplication(status)
                .date(date)
                .build();
    }

    public ApplicationForMarking build(StatusMarkingApplication statusMarkingApplication, Product product) {
        return ApplicationForMarking.builder()
                .product(product)
                .typeMarking(typeMarking)
                .statusMarkingApplication(statusMarkingApplication)
                .date(date)
                .build();
    }
}
