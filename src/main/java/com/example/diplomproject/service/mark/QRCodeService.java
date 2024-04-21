package com.example.diplomproject.service.mark;

import com.example.diplomproject.model.entity.MarkingInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
@Slf4j
public class QRCodeService implements GenerateCodeForMarking<MarkingInfo> {

    private static final int width = 300;
    private static final int height = 300;

    @Override
    public File generate(MarkingInfo data) {
        File outputFile = null;

        try {
            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix bitMatrix = writer.encode(data.toString(), BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = generateImage(bitMatrix, width, height);
            outputFile = new File("qrcode.png");
            ImageIO.write(image, "png", outputFile);

            System.out.println("QR-код сгенерирован и сохранен в файле: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return outputFile;
    }
}
