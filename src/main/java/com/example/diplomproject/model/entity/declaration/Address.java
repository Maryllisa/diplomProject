package com.example.diplomproject.model.entity.declaration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address implements UserType {
    private String city;
    private String postalCode;
    private String region;
    private String settlement;
    private String build;
    private String ogrnNumber;
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return Address.class;
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
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        // Извлечение данных из базы данных и преобразование их в экземпляр класса Address
        String city = rs.getString(names[0]);
        String postalCode = rs.getString(names[1]);
        String region = rs.getString(names[2]);
        String settlement = rs.getString(names[3]);
        String build = rs.getString(names[4]);
        String ogrnNumber = rs.getString(names[5]);

        return new Address(city, postalCode, region, settlement, build, ogrnNumber);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            Address address = (Address) value;
            st.setString(index, address.getCity());
            st.setString(index + 1, address.getPostalCode());
            st.setString(index + 2, address.getRegion());
            st.setString(index + 3, address.getSettlement());
            st.setString(index + 4, address.getBuild());
            st.setString(index + 5, address.getOgrnNumber());
        }
    }
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        Address address = (Address) value;
        return new Address(address.getCity(), address.getPostalCode(), address.getRegion(), address.getSettlement(),
                address.getBuild(), address.getOgrnNumber());
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
