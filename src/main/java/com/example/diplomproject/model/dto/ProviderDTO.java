package com.example.diplomproject.model.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDTO {

    private String nameProvider;
    private String address;
    private String build;

    private float evaluationProvider;


}
