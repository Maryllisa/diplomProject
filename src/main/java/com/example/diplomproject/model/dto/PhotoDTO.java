package com.example.diplomproject.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDTO {

    private String namePhoto;
    private float sizePhoto;

    private String photo;

}
