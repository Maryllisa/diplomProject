package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.repository.*;
import com.example.diplomproject.service.mark.BarcodeService;
import com.example.diplomproject.service.mark.DataMatrixCodeService;
import com.example.diplomproject.service.mark.QRCodeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
    private final UserRepository userRepository;
    private final CustomsAgencyRepository customsAgencyRepository;
    private final DeliveryProductRepository deliveryProductRepository;

    @SneakyThrows
    public void addNewMarking(MarkingInfoDTO markingInfoDTO) {
        Product product = productRepository.findById(markingInfoDTO.getIdProduct()).orElse(null);
        ApplicationForMarking applicationForMarking = applicationForMarkingRepository.findAllByProduct(product);
        markingInfoDTO.setProduct(product.build());
        MarkingInfo markingInfo = markingInfoDTO.build();
        markingInfo.setDatePut(Date.valueOf(LocalDate.now()));
        markingInfo.setApplicationForMarking(applicationForMarking);
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
        markingInfo = markingInfoRepository.save(markingInfo);
        applicationForMarking.setMarkingInfo(markingInfo);
        applicationForMarking.setStatusMarkingApplication(StatusMarkingApplication.COMPLETED);
        DeliveryProduct deliveryProduct = null;
        List<DeliveryProduct> deliveryProducts = deliveryProductRepository.findAll();
        for(DeliveryProduct x : deliveryProducts){
            if (x.getProductList().stream().map(Product::getIdProduct)
                    .anyMatch(y->y.equals(product.getIdProduct()))) {
                deliveryProduct = x;
                break;
            }
        }
        CustomsAgency customsAgency = new CustomsAgency();
        if (deliveryProduct!=null) {
            customsAgency = customsAgencyRepository.findByDeliveryProduct(deliveryProduct).orElse(null);
        }
        customsAgency.setMarkingInfo(markingInfo);
        customsAgencyRepository.save(customsAgency);
        applicationForMarkingRepository.save(applicationForMarking);
    }

    public MarkingInfo findById(Long i) {
        return markingInfoRepository.findById(i).orElse(null);
    }

    public List<MarkingInfo> getAllMarking() {
        return markingInfoRepository.findAll();
    }

    public List<MarkingInfo> getAllMarking(String login) {
        Account account = userRepository.findByLogin(login);
        return markingInfoRepository.findAllByAccount(account);
    }
}
