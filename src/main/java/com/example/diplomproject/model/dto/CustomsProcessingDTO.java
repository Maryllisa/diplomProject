package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.CustomsProcessing;
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
public class CustomsProcessingDTO {

    private String customsPostName;
    private String customsCode;
    private String svhNameAndAddress;
    private String licenseNumber;
    private Date issueDate;


}
