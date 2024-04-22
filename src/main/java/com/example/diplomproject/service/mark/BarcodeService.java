package com.example.diplomproject.service.mark;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
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
public class BarcodeService implements GenerateCodeForMarking<ProductDTO>  {
    private static final int width = 300;
    private static final int  height = 150;

    @Override
    public MultipartFile generate(ProductDTO data) {
        MultipartFile multipartFile = null;
        try {

            String productInformation = data.getIdProduct() + ' ' + data.getNumberDeclaration();
            Code128Writer writer = new Code128Writer();

            BitMatrix bitMatrix = writer.encode(productInformation, BarcodeFormat.CODE_128, width, height);

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
