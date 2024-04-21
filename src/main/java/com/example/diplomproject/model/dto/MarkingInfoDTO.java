package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkingInfoDTO {

    private Long idMarkingInfo;

    private TypeMarking typeMarking;

    private String srcCode;

    private String size;
    private Long idProduct;
    private ProductDTO product;

    public MarkingInfo build(){
        return MarkingInfo.builder()
                .idMarkingInfo(idMarkingInfo)
                .typeMarking(typeMarking)
                .srcCode(srcCode)
                .size(size)
                .product(product.build())
                .build();
    }

}
