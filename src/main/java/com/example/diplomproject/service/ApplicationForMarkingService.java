package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.repository.ApplicationForMarkingRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationForMarkingService {
    private final ApplicationForMarkingRepository applicationForMarkingRepository;
    private final UserRepository accountRepository;
    private final ProductRepository productRepository;

    public List<ApplicationForMarkingDTO> getAllApplicationsForMarking(String login) {
        if (applicationForMarkingRepository.findAllByAccount(accountRepository.findByLogin(login)).isEmpty())
            return new ArrayList<>();
        List<ApplicationForMarking> applicationForMarking = applicationForMarkingRepository.findAllByAccount(accountRepository.findByLogin(login)).orElse(null);
        List<ApplicationForMarkingDTO> applicationForMarkingDTOList = new ArrayList<>();
        applicationForMarking.forEach(x->{
            applicationForMarkingDTOList.add(x.build());
        });
        return applicationForMarkingDTOList;
    }

    public void addNewApplicationForMarking(ApplicationForMarkingDTO applicationForMarkingDTO,
                                            String login) {
        Product product = productRepository.findById(applicationForMarkingDTO.getIdProduct()).orElse(null);
        ApplicationForMarking applicationForMarking = applicationForMarkingDTO.build(StatusMarkingApplication.PENDING,product);
        applicationForMarking.setAccount(accountRepository.findByLogin(login));
        applicationForMarking.setDate(Date.valueOf(LocalDate.now()));
        applicationForMarkingRepository.save(applicationForMarking);
    }
}
