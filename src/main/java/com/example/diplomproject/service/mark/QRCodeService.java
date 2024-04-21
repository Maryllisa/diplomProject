package com.example.diplomproject.service.mark;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import org.springframework.mock.web.MockMultipartFile;

@Service
@Slf4j
public class QRCodeService implements GenerateCodeForMarking<ProductDTO> {

    private static final int width = 300;
    private static final int height = 300;

    @Override
    public MultipartFile generate(ProductDTO data) {
        MultipartFile multipartFile = null;

        try {
            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix bitMatrix = writer.encode(data.toString(), BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = generateImage(bitMatrix, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            multipartFile = new MockMultipartFile("qrcode.png", baos.toByteArray());

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return multipartFile;
    }
}
