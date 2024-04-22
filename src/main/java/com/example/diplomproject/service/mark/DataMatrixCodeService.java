package com.example.diplomproject.service.mark;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class DataMatrixCodeService implements GenerateCodeForMarking<ProductDTO> {
    private static final int width = 300;
    private static final int height =300;
    @Override
    public MultipartFile generate(ProductDTO data){
        MultipartFile multipartFile = null;
        try {
            DataMatrixWriter writer = new DataMatrixWriter();

            byte[] encodedData = data.toString().getBytes(StandardCharsets.UTF_8);

            // Generate the BitMatrix using the encoded data
            BitMatrix bitMatrix = writer.encode(new String(encodedData, StandardCharsets.ISO_8859_1),
                    BarcodeFormat.DATA_MATRIX, width, height);

            BufferedImage image = generateImage(bitMatrix, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            multipartFile = new MockMultipartFile("datamatrix.png", baos.toByteArray());


        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return multipartFile;
    }
}
