package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String city;
    private String postalCode;
    private String region;
    private String settlement;
    private String build;
    private String ogrnNumber;

    public Address build() {
        return  new Address(city,
                postalCode,
                region,
                settlement,
                build,
                ogrnNumber);
    }
}
