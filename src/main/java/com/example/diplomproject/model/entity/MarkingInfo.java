package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import javassist.bytecode.analysis.MultiType;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Base64;

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
    @SneakyThrows
    public void addMarking(MultipartFile file){
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(file.getBytes());
        this.size = String.valueOf(file.getSize());
        this.srcCode = encoded;
        this.originalFileName = file.getName();
        this.contentType = MediaType.IMAGE_PNG.getType();
    }

}
