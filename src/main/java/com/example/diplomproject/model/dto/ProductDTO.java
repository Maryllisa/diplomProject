package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long idProduct;
    private int itemNumber;
    private String productCode;
    private String originCountryCode;
    private String nameProduct;
    private double grossWeight;
    private String preference;
    private String procedure;
    private double netWeight;
    private String quota;

    public Product build(){
        return Product.builder()
                .itemNumber(itemNumber)
                .productCode(productCode)
                .originCountryCode(originCountryCode)
                .nameProduct(nameProduct)
                .grossWeight(grossWeight)
                .preference(preference)
                .procedure(procedure)
                .netWeight(netWeight)
                .quota(quota)
                .build();
    }

}
