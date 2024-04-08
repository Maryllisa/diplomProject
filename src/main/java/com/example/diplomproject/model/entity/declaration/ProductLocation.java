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
public class ProductLocation implements UserType {

    private String uzoRegistry;
    private String customsCode;
    private String type;
    private String quantity;
    private String documentNumber;
    private Date date;
    private String ztkNumber;
    private String transportType;
    private String vehicleNumber;
    private String stationOrPort;
    private String country;
    private String postalCode;
    private String regionOrDistrict;
    private String locality;
    private String houseNumber;

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
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String uzoRegistry = rs.getString(names[0]);
        String customsCode = rs.getString(names[1]);
        String type = rs.getString(names[2]);
        String quantity = rs.getString(names[3]);
        String documentNumber = rs.getString(names[4]);
        Date date = rs.getDate(names[5]);
        String ztkNumber = rs.getString(names[6]);
        String transportType = rs.getString(names[7]);
        String vehicleNumber = rs.getString(names[8]);
        String stationOrPort = rs.getString(names[9]);
        String country = rs.getString(names[10]);
        String postalCode = rs.getString(names[11]);
        String regionOrDistrict = rs.getString(names[12]);
        String locality = rs.getString(names[13]);
        String houseNumber = rs.getString(names[14]);
        return new ProductLocation(uzoRegistry, customsCode, type,quantity, documentNumber,date,ztkNumber, transportType,
                vehicleNumber,stationOrPort,country,postalCode,regionOrDistrict,locality,houseNumber);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            ProductLocation productLocation = (ProductLocation) value;
            st.setString(index, productLocation.getUzoRegistry());
            st.setString(index + 1, productLocation.getCustomsCode());
            st.setString(index + 2, productLocation.getType());
            st.setString(index + 4, productLocation.getQuantity());
            st.setString(index + 5, productLocation.getDocumentNumber());
            st.setDate(index + 6, productLocation.getDate());
            st.setString(index + 7, productLocation.getZtkNumber());
            st.setString(index + 8, productLocation.getTransportType());
            st.setString(index + 9, productLocation.getVehicleNumber());
            st.setString(index + 10, productLocation.getStationOrPort());
            st.setString(index +11, productLocation.getCountry());
            st.setString(index + 12, productLocation.getPostalCode());
            st.setString(index + 13, productLocation.getRegionOrDistrict());
            st.setString(index + 14, productLocation.getLocality());
            st.setString(index + 15, productLocation.getHouseNumber());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        ProductLocation productLocation = (ProductLocation) value;
        return new ProductLocation(productLocation.getUzoRegistry(), productLocation.getCustomsCode(),productLocation.getType(), productLocation.getQuantity(),
                productLocation.getDocumentNumber(), productLocation.getDate(), productLocation.getZtkNumber(), productLocation.getTransportType(),
                productLocation.getVehicleNumber(), productLocation.getStationOrPort(), productLocation.getCountry(),
                productLocation.getPostalCode(), productLocation.getRegionOrDistrict(), productLocation.getLocality(),
                productLocation.getHouseNumber());
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
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object o1, Object o2) throws HibernateException {
        return original;
    }
}
