package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.repository.IndividualsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndividualsService {

    private final IndividualsRepository individualsRepository;

    public List<Individuals> getAllSuppliers() {
        return individualsRepository.findAll();
    }
}
