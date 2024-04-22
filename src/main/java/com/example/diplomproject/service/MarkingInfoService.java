package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.repository.ApplicationForMarkingRepository;
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

@Service
@Slf4j
@AllArgsConstructor
public class MarkingInfoService {
    private final MarkingInfoRepository markingInfoRepository;
    private final ProductRepository productRepository;
    private final QRCodeService qrCodeService;
    private final DataMatrixCodeService dataMatrixCodeService;
    private final BarcodeService barcodeService;
    private final ApplicationForMarkingRepository applicationForMarkingRepository;
    @SneakyThrows
    public void addNewMarking(MarkingInfoDTO markingInfoDTO) {
        Product product = productRepository.findById(markingInfoDTO.getIdProduct()).orElse(null);
        ApplicationForMarking applicationForMarking = applicationForMarkingRepository.findAllByProduct(product);
        markingInfoDTO.setProduct(product.build());
        MarkingInfo markingInfo = markingInfoDTO.build();
        if (markingInfoDTO.getTypeMarking().equals(TypeMarking.ACCISE_MARK)){
            MultipartFile file = qrCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);

        } else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.CONTROL_MARK)) {
            MultipartFile file = qrCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.RFID_MARK)) {
            MultipartFile file = qrCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.STICKER)) {
            MultipartFile file = barcodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.DATAMATRIX_CODE)) {
            MultipartFile file = dataMatrixCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        applicationForMarking.setMarkingInfo(markingInfoRepository.save(markingInfo));
        applicationForMarking.setStatusMarkingApplication(StatusMarkingApplication.COMPLETED);
        applicationForMarkingRepository.save(applicationForMarking);
    }

    public MarkingInfo findById(int i) {
        return markingInfoRepository.findById(Long.valueOf(i)).orElse(null);
    }
}
