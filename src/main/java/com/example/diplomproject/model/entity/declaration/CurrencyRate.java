package com.example.diplomproject.model.entity.declaration;

import com.example.diplomproject.model.dto.dtoForDeclaration.CurrencyRateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public CurrencyRate(Date date, String currency, double currencyRate, double euroRate, double usdRate) {
        this.date = date;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.euroRate = euroRate;
        this.usdRate = usdRate;
    }
    public CurrencyRateDTO build(){
        return new CurrencyRateDTO(date, currency, currencyRate,euroRate,usdRate);
    }
}
