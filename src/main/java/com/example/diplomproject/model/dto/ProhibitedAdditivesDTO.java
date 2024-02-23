package com.example.diplomproject.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProhibitedAdditivesDTO {

    private String nameAdditives;
    private float evaluationAdditives;
}
