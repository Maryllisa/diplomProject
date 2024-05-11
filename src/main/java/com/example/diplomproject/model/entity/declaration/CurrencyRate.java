package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import lombok.*;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CurrencyRate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurrencyRate;
    @Column
    private Date date;
    @Column
    private String currency;
    @Column
    private double currencyRate;
    @Column
    private double euroRate;
    @Column
    private double usdRate;

    public CurrencyRateDTO build() {
        return CurrencyRateDTO.builder()
                .date(date)
                .currency(currency)
                .currencyRate(currencyRate)
                .euroRate(euroRate)
                .usdRate(usdRate)
                .build();
    }

    public boolean check() {
        return this.date!=null && this.currency!=null && this.currencyRate!=0 && this.euroRate!=0 && this.usdRate!=0;
    }
}
