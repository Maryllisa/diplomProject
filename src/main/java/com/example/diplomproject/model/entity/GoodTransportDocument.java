package com.example.diplomproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GoodTransportDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGoodTransportDocument;
    @Column
    private String goodsTransportDocumentNumbers;
    @Column
    @Lob
    private String srcTransportDocument;
    @ManyToOne
    private Account account;

    public String getEncoding(){
       return Base64.getEncoder().encodeToString(this.srcTransportDocument.getBytes());
    }
}
