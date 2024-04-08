package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int itemNumber;
    private String productCode;
    private String originCountryCode;
    private double grossWeight;
    private String preference;
    private String procedure;
    private double netWeight;
    private String quota;

    public Product build() {
        Product product = new Product();
        product.setItemNumber(itemNumber);
        product.setProductCode(productCode);
        product.setOriginCountryCode(originCountryCode);
        product.setGrossWeight(grossWeight);
        product.setPreference(preference);
        product.setProcedure(procedure);
        product.setNetWeight(netWeight);
        product.setQuota(quota);
        return product;
    }
}
