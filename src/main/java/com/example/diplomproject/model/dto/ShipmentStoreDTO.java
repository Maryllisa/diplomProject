package com.example.diplomproject.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentStoreDTO {

    private String nameStore;
    private Date date;
    private String address;
    private String status;
    private float evaluationDelivery;


}
