package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.StatusApplication;
import com.example.diplomproject.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public List<ApplicationForStorage> getAllApplication() {
        return applicationRepository.findAll();
    }

    public List<ApplicationForStorage> getApplicationTrue(StatusApplication statusApplication) {
        return applicationRepository.findByStatusApplication(statusApplication);
    }
}
