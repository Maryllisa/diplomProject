package com.example.diplomproject.model.dto.dtoForDeclaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRateDTO {

    private Date date;
    private String currency;
    private double currencyRate;
    private double euroRate;
    private double usdRate;
}