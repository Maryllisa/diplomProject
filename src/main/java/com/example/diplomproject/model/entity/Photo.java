package com.example.diplomproject.model.entity;

import javax.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String namePhoto;
    @Column
    private float sizePhoto;
    @Column
    @Lob
    private String photo;
}
