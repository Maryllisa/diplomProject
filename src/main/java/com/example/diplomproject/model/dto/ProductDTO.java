package com.example.diplomproject.model.dto;

import com.example.diplomproject.model.entity.Conditions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private String description;

    private List<String> composition;
    private float weightProduct;

}
