package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.repository.ApplicationRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public List<ApplicationForStorage> getAllApplication() {
        return applicationRepository.findAll();
    }

    public List<ApplicationForStorage> getApplicationTrue(StatusApplication statusApplication) {
        return applicationRepository.findByStatusApplication(statusApplication);
    }


    public void changeStatus(StatusApplication statusApplication, String id, String login) {
        Account account = userRepository.findByLogin(login);
        applicationRepository.findById(Long.valueOf(id)).ifPresent(applicationForStorage -> {
            applicationForStorage.setAccountManager(account);
                applicationForStorage.setStatusApplication(statusApplication);
                applicationRepository.save(applicationForStorage);
        });

    }
}
