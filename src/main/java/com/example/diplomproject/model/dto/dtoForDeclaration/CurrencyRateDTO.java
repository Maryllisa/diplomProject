package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.config.annotation.imp.ValidDateOfBirthRange;
import com.example.diplomproject.model.entity.declaration.CurrencyRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyRateDTO {
    @NotEmpty
    @ValidDateOfBirthRange
    private Date date;
    @NotEmpty
    private String currency;
    @NotEmpty
    private double currencyRate;
    @NotEmpty
    private double euroRate;
    @NotEmpty
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
