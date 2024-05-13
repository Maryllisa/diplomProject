package com.example.diplomproject.model.dto;

import com.example.diplomproject.config.annotation.imp.ValidDate;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryProductDTO {

    private Long idDeliveryProduct;
    @Min(value = 1)
    private double weightProduct;
    @ValidDate
    private Date arrangeDate;
    @NotEmpty
    private String prodCondition;
    @Min(value = 1)
    private double deliveryEvalution;
    @Min(value = 0)
    private Long idApplicationForStorage;
    @NotNull
    private Map<Long, Boolean> checkProduct = new HashMap<>();


}
