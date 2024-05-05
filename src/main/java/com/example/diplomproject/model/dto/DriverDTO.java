package com.example.diplomproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String name;
    @NotNull
    @NotEmpty
    private String LicenseNumber;
}
