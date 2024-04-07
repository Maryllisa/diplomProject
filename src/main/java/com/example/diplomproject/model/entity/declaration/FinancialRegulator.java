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
public class FinancialRegulator implements UserType {
    private String innKpp;
    private String organizationName;
    private String country;
    private String postalCode;
    private String region;
    private String locality;
    private String street;
    private String ogrn;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class<?> returnedClass() {
        return FinancialRegulator.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException {
        // Извлечение данных из базы данных и преобразование их в экземпляр класса Address
        String innKpp = rs.getString(names[0]);
        String organizationName = rs.getString(names[1]);
        String country = rs.getString(names[2]);
        String postalCode = rs.getString(names[3]);
        String region = rs.getString(names[4]);
        String locality = rs.getString(names[5]);
        String street = rs.getString(names[6]);
        String ogrn = rs.getString(names[7]);

        return new FinancialRegulator(innKpp, organizationName, country, postalCode, region, locality, street,ogrn);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            FinancialRegulator financialRegulator = (FinancialRegulator) value;
            st.setString(index, financialRegulator.getInnKpp());
            st.setString(index + 1, financialRegulator.getOrganizationName());
            st.setString(index + 2, financialRegulator.getCountry());
            st.setString(index + 3, financialRegulator.getPostalCode());
            st.setString(index + 4, financialRegulator.getRegion());
            st.setString(index + 5, financialRegulator.getLocality());
            st.setString(index + 6, financialRegulator.getStreet());
            st.setString(index + 7, financialRegulator.getOgrn());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        FinancialRegulator financialRegulator = (FinancialRegulator) value;
        return new FinancialRegulator(
                financialRegulator.getInnKpp(),
                financialRegulator.getOrganizationName(),
                financialRegulator.getCountry(),
                financialRegulator.getPostalCode(),
                financialRegulator.getRegion(),
                financialRegulator.getLocality(),
                financialRegulator.getStreet(),
                financialRegulator.getOgrn()
        );
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
