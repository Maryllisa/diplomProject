package com.example.diplomproject.model.dto.dtoForDeclaration;

import com.example.diplomproject.model.entity.declaration.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String city;
    @NotEmpty
    @Pattern(regexp = "^[0-9]{5}$")
    private String postalCode;
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String region;
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+$")
    private String settlement;
    @NotEmpty
    @Pattern(regexp = "^\\d+$")
    private String build;
    @NotEmpty
    @Pattern(regexp = "[АВЕКМНОРСТУХABEKMHOPCTYX]\\d{3}[АВЕКМНОРСТУХABEKMHOPCTYX]{2}\\d{2,3}")
    private String ogrnNumber;

    public Address build(){
        return Address.builder()
                .city(city)
                .postalCode(postalCode)
                .region(region)
                .settlement(settlement)
                .build(build)
                .ogrnNumber(ogrnNumber)
                .build();
    }

}
