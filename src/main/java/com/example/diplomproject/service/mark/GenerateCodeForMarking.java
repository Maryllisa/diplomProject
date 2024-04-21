package com.example.diplomproject.service.mark;


import com.google.zxing.common.BitMatrix;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;

public interface GenerateCodeForMarking<T> {

    public File generate(T data);
    public default BufferedImage generateImage(BitMatrix bitMatrix, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return image;
    }
}
