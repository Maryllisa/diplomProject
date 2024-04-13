package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.CustomsProcessingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public CustomsProcessing(String customsPostName, String customsCode,
                             String svhNameAndAddress, String licenseNumber,
                             Date issueDate) {
        this.customsPostName = customsPostName;
        this.customsCode = customsCode;
        this.svhNameAndAddress = svhNameAndAddress;
        this.licenseNumber = licenseNumber;
        this.issueDate = issueDate;
    }
    public CustomsProcessingDTO build(){
        return new CustomsProcessingDTO(customsPostName, customsCode,
                svhNameAndAddress, licenseNumber, issueDate);
    }
}
