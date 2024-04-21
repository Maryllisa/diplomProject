package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.repository.MarkingInfoRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.service.mark.BarcodeService;
import com.example.diplomproject.service.mark.DataMatrixCodeService;
import com.example.diplomproject.service.mark.QRCodeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class MarkingInfoService {
    private final MarkingInfoRepository markingInfoRepository;
    private final ProductRepository productRepository;
    private final QRCodeService qrCodeService;
    private final DataMatrixCodeService dataMatrixCodeService;
    private final BarcodeService barcodeService;
    @SneakyThrows
    public void addNewMarking(MarkingInfoDTO markingInfoDTO) {
        Product product = productRepository.findById(markingInfoDTO.getIdProduct()).orElse(null);
        markingInfoDTO.setProduct(product.build());
        MarkingInfo markingInfo = markingInfoDTO.build();
        if (markingInfoDTO.getTypeMarking().equals(TypeMarking.ACCISE_MARK)){
            MultipartFile file = qrCodeService.generate(product.build());
            Base64.Encoder encoder = Base64.getEncoder();
            String encoded = encoder.encodeToString(file.getBytes());
            markingInfo.setSize(String.valueOf(file.getSize()));
            markingInfo.setSrcCode(encoded);
            markingInfo.setOriginalFileName(file.getName());
            markingInfo.setContentType(file.getContentType());
            markingInfo.setProduct(product);

        } else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.CONTROL_MARK)) {

        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.RFID_MARK)) {

        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.STICKER)) {

        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.DATAMATRIX_CODE)) {

        }
        markingInfoRepository.save(markingInfo);
    }

    public MarkingInfo findById(int i) {
        return markingInfoRepository.findById(Long.valueOf(i)).orElse(null);
    }
}
