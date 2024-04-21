package com.example.diplomproject.service.mark;

import com.example.diplomproject.model.entity.MarkingInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class BarcodeService implements GenerateCodeForMarking<MarkingInfo>  {
    private static final int width = 300;
    private static final int  height = 150;

    @Override
    public MultipartFile generate(MarkingInfo data) {
        MultipartFile multipartFile = null;
        try {
            EAN13Writer writer = new EAN13Writer();

            BitMatrix bitMatrix = writer.encode(data.toString(), BarcodeFormat.EAN_13, width, height);

            BufferedImage image = generateImage(bitMatrix, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            multipartFile = new MockMultipartFile("barcode.png", baos.toByteArray());

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return multipartFile;
    }



}
