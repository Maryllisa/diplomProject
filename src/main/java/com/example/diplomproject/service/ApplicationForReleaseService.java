package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ApplicationForReleaseDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForRelease;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.repository.ApplicationForReleaseRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.repository.UserRepository;
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
}
