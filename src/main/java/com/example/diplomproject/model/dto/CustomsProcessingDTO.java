package com.example.diplomproject.model.dto;

import com.example.diplomproject.config.annotation.imp.ValidDate;
import com.example.diplomproject.model.entity.CustomsProcessing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomsProcessingDTO {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "\\d+")
    private String customsPostName;
    @NotNull
    @NotEmpty
    private String customsCode;
    @NotNull
    @NotEmpty
    private String svhNameAndAddress;
    @NotNull
    @NotEmpty
    private String licenseNumber;
    @NotNull
    @ValidDate
    private Date issueDate;


}
