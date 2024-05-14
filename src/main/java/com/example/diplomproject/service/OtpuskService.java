package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpuskService {
    private final OtpuskRepository otpuskRepository;
    private final ApplicationForReleaseRepository applicationForRelease;
    private final UserRepository userRepository;
    private final DeliveryProductRepository deliveryProductRepository;
    private final CustomsAgencyRepository customsAgencyRepository;

    @SneakyThrows
    public void addOtpusk(Long idApplicationForRelease, double sumForStorage, MultipartFile file) {
        log.info("ДОБАВЛЕНИЕ НОВОЙ ОПЛАТЫ");
        Otpusk otpusk = new Otpusk();
        otpusk.setNameSrc(file.getName());
        otpusk.setSize(file.getSize());
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(file.getBytes());
        otpusk.setSrc(encoded);
        log.info("ПОИСК ЗАЯВЛЕНИЕ НА РЕАЛИЗАЦИЮ");
        ApplicationForRelease application =applicationForRelease.getById(idApplicationForRelease);
        application.setStatusApplicationForRelease(StatusApplicationForRelease.PAID);
        otpusk.setApplicationForRelease(application);
        otpusk.setSumForStorage(sumForStorage);
        otpusk = otpuskRepository.save(otpusk);
        DeliveryProduct deliveryProduct = null;
        List<DeliveryProduct> deliveryProducts = deliveryProductRepository.findAll();
        for(DeliveryProduct x : deliveryProducts){
            if (x.getProductList().stream().map(Product::getIdProduct)
                    .anyMatch(y->y.equals(application.getProduct().getIdProduct()))) {
                deliveryProduct = x;
                break;
            }
        }
        CustomsAgency customsAgency = new CustomsAgency();
        if (deliveryProduct!=null) {
            customsAgency = customsAgencyRepository.findByDeliveryProduct(deliveryProduct).orElse(null);
        }
        customsAgency.setOtpusk(otpusk);
        customsAgencyRepository.save(customsAgency);

    }

    public List<Otpusk> getAllByLogin(String name) {
        return otpuskRepository.findAllByApplicationForRelease(userRepository.findByLogin(name));
    }

    public Otpusk getByApplicationForRelease(Long id) {
        return otpuskRepository.findAllByApplicationForRelease(applicationForRelease.getById(id));
    }

    public List<Otpusk> getAllByAccountClient(String name) {
        return otpuskRepository.findAllByClient(userRepository.findByLogin(name));
    }
}
