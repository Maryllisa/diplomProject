package com.example.diplomproject.service.mark;

import com.example.diplomproject.model.entity.MarkingInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class BarcodeService implements GenerateCodeForMarking<MarkingInfo>  {
    private static final int width = 300;
    private static final int  height = 150;

    @Override
    public File generate(MarkingInfo data) {
        File outputFile = null;
        try {
            EAN13Writer writer = new EAN13Writer();

            BitMatrix bitMatrix = writer.encode(data.toString(), BarcodeFormat.EAN_13, width, height);

            BufferedImage image = generateImage(bitMatrix, width, height);
            outputFile = new File("barcode.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Штрихкод сгенерирован и сохранен в файле: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return  outputFile;
    }



}
