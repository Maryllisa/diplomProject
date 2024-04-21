package com.example.diplomproject.service.mark;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.example.diplomproject.model.entity.MarkingInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataMatrixCodeService implements GenerateCodeForMarking<MarkingInfo> {
    private static final int width = 300;
    private static final int height =300;
    @Override
    public File generate(MarkingInfo data){
        File outputFile = null;
        try {
            DataMatrixWriter writer = new DataMatrixWriter();

            BitMatrix bitMatrix = writer.encode(data.toString(), BarcodeFormat.DATA_MATRIX, width, height);

            BufferedImage image = generateImage(bitMatrix, width, height);

            outputFile = new File("datamatrix.png");
            ImageIO.write(image, "png", outputFile);

            System.out.println("DataMatrix сгенерирован и сохранен в файле: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return outputFile;
    }
}
