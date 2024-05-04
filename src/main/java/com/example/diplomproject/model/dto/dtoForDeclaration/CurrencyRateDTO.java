package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;
import com.example.diplomproject.model.entity.declaration.CurrencyRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyRateDTO {
    @NotNull
    @NotEmpty
    private Date date;
    @NotNull
    @NotEmpty
    private String currency;
    private double currencyRate;
    private double euroRate;
    private double usdRate;

    public CurrencyRate build() {
        return CurrencyRate.builder()
                .date(date)
                .currency(currency)
                .currencyRate(currencyRate)
                .euroRate(euroRate)
                .usdRate(usdRate)
                .build();
    }
}
