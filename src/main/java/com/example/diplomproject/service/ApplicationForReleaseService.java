package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ApplicationForReleaseDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationForReleaseService {
    private final ApplicationForReleaseRepository applicationForReleaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DeliveryProductRepository deliveryProductRepository;
    private final CustomsAgencyRepository customsAgencyRepository;

    public List<ApplicationForReleaseDTO> getAllApplicationForRelease(String name) {
        List<ApplicationForRelease> applicationForReleaseList = applicationForReleaseRepository
                .findAllByAccount(userRepository.findByLogin(name));
        List<ApplicationForReleaseDTO> applicationForReleaseDTOList = new ArrayList<>();
        applicationForReleaseList.forEach(x->{
            applicationForReleaseDTOList.add(x.build());
        });
        return applicationForReleaseDTOList;
    }

    public void addNewApplicationForRelease(ApplicationForReleaseDTO applicationForReleaseDTO,
                                            String name) {
        Account account = userRepository.findByLogin(name);
        Product product = productRepository.findById(applicationForReleaseDTO.getIdProduct()).orElse(null);
        ApplicationForRelease applicationForRelease = applicationForReleaseDTO.build(product, StatusApplicationForRelease.IN_PROCESSING);
        applicationForRelease.setAccount(account);
        applicationForRelease.setStatusApplicationForRelease(StatusApplicationForRelease.IN_PROCESSING);
        ApplicationForRelease application = applicationForReleaseRepository.save(applicationForRelease);
        if (product != null) {
            product.setApplicationForRelease(applicationForRelease);
            productRepository.save(product);
        }

    }

    public void deleteApplication(Long id) {
        ApplicationForRelease application = applicationForReleaseRepository.getById(id);
        Product product = productRepository.getById(application.getProduct().getIdProduct());
        product.setApplicationForRelease(null);
        productRepository.save(product);
        applicationForReleaseRepository.delete(application);
    }

    public List<ApplicationForRelease> getAllApplicationForRelease() {
        return applicationForReleaseRepository.findAll();
    }

    public void changeStatus(Long id, StatusApplicationForRelease statusApplicationForRelease) {
        ApplicationForRelease application = applicationForReleaseRepository.getById(id);
        application.setStatusApplicationForRelease(statusApplicationForRelease);

        applicationForReleaseRepository.save(application);
    }

    public List<ApplicationForRelease> getAllApplicationForReleaseAndStatus(StatusApplicationForRelease statusApplicationForRelease, String login) {
        List<ApplicationForRelease> applicationForReleaseList = applicationForReleaseRepository.findAllByStatusApplicationForReleaseAndAccount(statusApplicationForRelease, userRepository.findByLogin(login));
        applicationForReleaseList.addAll(applicationForReleaseRepository.findAllByStatusApplicationForReleaseAndAccount(StatusApplicationForRelease.PAID, userRepository.findByLogin(login)));
        return applicationForReleaseList;
    }

    public ApplicationForRelease getApplicationForReleaseById(Long id) {
        return applicationForReleaseRepository.getById(id);
    }

    public List<ApplicationForRelease> getAllApplicationForReleaseAndStatus(String login, StatusApplicationForRelease statusApplicationForRelease) {
        return applicationForReleaseRepository.findAllByStatusApplicationForReleaseAndAccount(statusApplicationForRelease, userRepository.findByLogin(login));
    }
}
