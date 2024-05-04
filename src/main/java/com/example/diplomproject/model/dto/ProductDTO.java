package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long idProduct;
    private String productCode;
    private String originCountryCode;
    private String nameProduct;
    private Date date;
    private Date finalDate;
    private double grossWeight;
    private String preference;
    private String procedure;
    private double netWeight;
    private String quota;
    private String numberDeclaration;

    public Product build(){
        return Product.builder()
                .idProduct(idProduct)
                .productCode(productCode)
                .originCountryCode(originCountryCode)
                .nameProduct(nameProduct)
                .date(date)
                .finalDate(finalDate)
                .grossWeight(grossWeight)
                .preference(preference)
                .procedure(procedure)
                .netWeight(netWeight)
                .quota(quota)
                .build();
    }

}
