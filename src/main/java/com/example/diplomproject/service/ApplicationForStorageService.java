package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ApplicationForStorageDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.StatusApplication;
import com.example.diplomproject.repository.ApplicationForStorageRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationForStorageService {
    private final ApplicationForStorageRepository applicationForStorageRepository;
    private final UserRepository accountRepository;

    public void addNewApplication(ApplicationForStorageDTO applicationForStorageDTO, String name) {
        Account account = accountRepository.findByLogin(name);
        ApplicationForStorage applicationForStorage = applicationForStorageDTO.build(StatusApplication.PENDING);
        applicationForStorageRepository.save(applicationForStorage);
    }
}
