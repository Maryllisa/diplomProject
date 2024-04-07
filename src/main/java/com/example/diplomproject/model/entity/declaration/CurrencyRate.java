package com.example.diplomproject.model.entity.declaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate implements UserType {

    private Date date;
    private double currencyRate;
    private double euroRate;
    private double usdRate;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return CurrencyRate.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        Date date = resultSet.getDate(strings[0]);
        double currencyRate = resultSet.getDouble(strings[1]);
        double euroRate = resultSet.getDouble(strings[2]);
        double usdRate = resultSet.getDouble(strings[3]);

        return new CurrencyRate(date, currencyRate, euroRate, usdRate);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.OTHER);
        } else {
            CurrencyRate rate = (CurrencyRate) value;
            preparedStatement.setDate(index, rate.getDate());
            preparedStatement.setDouble(index + 1, rate.getCurrencyRate());
            preparedStatement.setDouble(index + 2, rate.getEuroRate());
            preparedStatement.setDouble(index + 3, rate.getUsdRate());

        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) {
            return null;
        }

        CurrencyRate rate = (CurrencyRate) o;
        return new CurrencyRate(rate.getDate(), rate.getCurrencyRate(), rate.getEuroRate(), rate.getUsdRate());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object o1, Object o2) throws HibernateException {
        return original;
    }
}
