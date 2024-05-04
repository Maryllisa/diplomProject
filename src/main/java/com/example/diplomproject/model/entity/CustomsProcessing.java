package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.CustomsProcessingDTO;
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
public class CustomsProcessing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomsProcessing;

    @Column
    private String customsPostName;
    @Column
    private String customsCode;
    @Column
    private String svhNameAndAddress;
    @Column
    private String licenseNumber;
    @Column
    private Date issueDate;

}
