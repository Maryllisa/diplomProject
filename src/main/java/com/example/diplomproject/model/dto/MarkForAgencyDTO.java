package com.example.diplomproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkForAgencyDTO {
    private List<Double> markQuality;
    private List<Double> prinProdQuality;
    private List<Double> otpProdQuality;
    private List<Double> comunicationQuality;
    private List<Double> qualityProduct;
}
