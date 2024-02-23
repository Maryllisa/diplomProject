package com.example.diplomproject.model.dto;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaEvaluationDTO {
    private String nameCriteria;
    private String descriptionCriteria;
}
