package com.example.diplomproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {
    private String nameAttachment;
    private float sizeAttachment;

    private String fileAttachment;
}
