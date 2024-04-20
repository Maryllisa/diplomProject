package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ApplicationForStorageDTO;
import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.StatusApplication;
import com.example.diplomproject.model.entity.Truck;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationForStorageService {
    private final ApplicationForStorageRepository applicationForStorageRepository;
    private final UserRepository accountRepository;
    private final GoodTransportDocumentRepository goodTransportDocumentRepository;
    private final DeclarationTDRepository declarationTDRepository;
    private final TruckRepository truckRepository;
    private final CRMRepository crmRepository;

    public void addNewApplication(ApplicationForStorageDTO applicationForStorageDTO, String name) {
        Account account = accountRepository.findByLogin(name);
        ApplicationForStorage applicationForStorage = applicationForStorageDTO.build(StatusApplication.PENDING,
                goodTransportDocumentRepository.getById(applicationForStorageDTO.getIdGoodTransportDocument()),
                declarationTDRepository.getById(applicationForStorageDTO.getIdDeclarationTD()),
                crmRepository.getById(applicationForStorageDTO.getIdCRM()),
                truckRepository.getById(applicationForStorageDTO.getIdTruck()));
        applicationForStorage.setAccount(account);
        applicationForStorageRepository.save(applicationForStorage);
    }

    public List<ApplicationForStorageDTO> getAllApplictionByAccount(String name) {
        List<ApplicationForStorageDTO> applicationForStorageDTOS = new ArrayList<>();
        List<ApplicationForStorage> applicationForStorages = applicationForStorageRepository.findAllByAccount(accountRepository.findByLogin(name));
        applicationForStorages.forEach(x->{
            applicationForStorageDTOS.add(x.build());
        });
        return applicationForStorageDTOS;
    }
}
