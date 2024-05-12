package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.MarkForAgency;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.repository.MarkForAgencyRepository;
import com.example.diplomproject.repository.MarkingInfoRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarkForAgencyService {
    private final MarkForAgencyRepository markForAgencyRepository;
    private final UserRepository userRepository;
    private final MarkingInfoRepository markingInfoRepository;

    public void addNewMarkQuality(Long idMark, MarkForAgency markForAgency, String name) {
        Account account = userRepository.findByLogin(name);
        markForAgency.setClient(account);
        MarkingInfo markingInfo = markingInfoRepository.getById(idMark);
        markingInfo.setMarkForAgency(markForAgencyRepository.save(markForAgency));
        markingInfoRepository.save(markingInfo);
    }
}
