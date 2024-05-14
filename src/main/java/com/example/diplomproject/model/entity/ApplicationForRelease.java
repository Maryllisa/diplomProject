package com.example.diplomproject.model.entity;

import com.example.diplomproject.model.dto.ApplicationForReleaseDTO;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ApplicationForRelease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplicationForRelease;
    @OneToOne
    private Product product;
    @Column
    private Date date;
    @Column
    @Enumerated(EnumType.STRING)
    private StatusApplicationForRelease statusApplicationForRelease;
    @JsonIgnore
    @ManyToOne
    private Account account;

    public ApplicationForReleaseDTO build() {
        return ApplicationForReleaseDTO.builder()
                .product(product.build())
                .idApplicationForRelease(idApplicationForRelease)
                .date(date)
                .statusApplicationForRelease(statusApplicationForRelease)
                .build();
    }
}
