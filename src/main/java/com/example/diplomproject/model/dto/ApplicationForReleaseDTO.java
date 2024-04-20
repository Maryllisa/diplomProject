package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.ApplicationForRelease;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.StatusApplication;
import com.example.diplomproject.model.entity.StatusApplicationForRelease;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationForReleaseDTO {
    private Long idApplicationForRelease;

    private Long idProduct;
    private ProductDTO product;
    private Date date;
    private StatusApplicationForRelease statusApplicationForRelease;

    public ApplicationForRelease build(Product product, StatusApplicationForRelease applicationForRelease) {
        return ApplicationForRelease.builder()
                .product(product)
                .date(date)
                .statusApplicationForRelease(applicationForRelease)
                .build();
    }
}
