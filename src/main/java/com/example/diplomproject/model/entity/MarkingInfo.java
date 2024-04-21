package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
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
@Entity
public class MarkingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarkingInfo;
    @Column
    @Enumerated(EnumType.STRING)
    private TypeMarking typeMarking;
    @Column
    @Lob
    private String srcCode;
    @Column
    private String size;
    @Column
    private String originalFileName;
    @Column
    private String contentType;
    @OneToOne
    private Product product;
    public MarkingInfoDTO build(){
        return MarkingInfoDTO.builder()
                .idMarkingInfo(idMarkingInfo)
                .idProduct(product.getIdProduct())
                .typeMarking(typeMarking)
                .srcCode(srcCode)
                .size(size)
                .product(product.build())
                .build();
    }

}