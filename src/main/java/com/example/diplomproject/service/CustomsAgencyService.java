package com.example.diplomproject.service;

import com.example.diplomproject.model.entity.CustomsAgency;
import com.example.diplomproject.model.entity.MarkForAgency;
import com.example.diplomproject.model.entity.enumStatus.TypeEvaluation;
import com.example.diplomproject.repository.CustomsAgencyRepository;
import com.example.diplomproject.repository.MarkForAgencyRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class CustomsAgencyService {
    private final CustomsAgencyRepository customsAgencyRepository;
    private final MarkForAgencyRepository markForAgencyRepository;

    public List<CustomsAgency> getAllNotMark() {
        return customsAgencyRepository.findAllByIsMark(false);
    }
    public List<CustomsAgency> getAllTrueMark() {
        return customsAgencyRepository.findAllByIsMark(true);
    }

    public List<MarkForAgency> getById(Long id) {
        return customsAgencyRepository.getById(id).getMarkForAgencies();
    }
    @SneakyThrows
    public void registrationMark(Long id,
                                 double vesochMark,
                                 double vesochOtgr,
                                 double vesochOtp,
                                 double vesochSviaz,
                                 double vesochSost) {
        CustomsAgency customsAgency = customsAgencyRepository.getById(id);
        List<MarkForAgency> markForAgencies = new ArrayList<>();
        MarkForAgency markVesochMark = markForAgencyRepository.findAllByCustomsAgencyAndTypeEvaluation(customsAgency, TypeEvaluation.markQuality)
                .orElse(null);
        MarkForAgency markVesochOtgr = markForAgencyRepository.findAllByCustomsAgencyAndTypeEvaluation(customsAgency, TypeEvaluation.prinProdQuality)
                .orElse(null);
        MarkForAgency markVesochOtp = markForAgencyRepository.findAllByCustomsAgencyAndTypeEvaluation(customsAgency, TypeEvaluation.otpProdQuality)
                .orElse(null);
        MarkForAgency markVesochSviaz = markForAgencyRepository.findAllByCustomsAgencyAndTypeEvaluation(customsAgency, TypeEvaluation.comunicationQuality)
                .orElse(null);
        MarkForAgency markVesochSost = markForAgencyRepository.findAllByCustomsAgencyAndTypeEvaluation(customsAgency, TypeEvaluation.qualityProduct)
                .orElse(null);

        if (markVesochMark!=null)
            markVesochMark.setWeightCoefficient(vesochMark);
        if (markVesochOtgr!=null)
            markVesochOtgr.setWeightCoefficient(vesochOtgr);
        if (markVesochOtp!=null)
            markVesochOtp.setWeightCoefficient(vesochOtp);
        if (markVesochSviaz!=null)
            markVesochSviaz.setWeightCoefficient(vesochSviaz);
        if (markVesochSost!=null)
            markVesochSost.setWeightCoefficient(vesochSost);
        List<MarkForAgency> markList = new ArrayList<>();
        markList.add(markForAgencyRepository.save(Objects.requireNonNull(markVesochMark)));
        markList.add(markForAgencyRepository.save(Objects.requireNonNull(markVesochOtgr)));
        markList.add(markForAgencyRepository.save(Objects.requireNonNull(markVesochOtp)));
        markList.add(markForAgencyRepository.save(Objects.requireNonNull(markVesochSviaz)));
        markList.add(markForAgencyRepository.save(Objects.requireNonNull(markVesochSost)));
        customsAgency.setMarkForAgencies(markList);
        customsAgency.setIsMark(true);
        customsAgency.setMark(getMark(customsAgency.getMarkForAgencies()));
        customsAgencyRepository.save(customsAgency);
    }

    public double getMark(List<MarkForAgency> markForAgencies){
        return markForAgencies.stream().mapToDouble(x->x.getWeightCoefficient()*x.getEvaluation()).sum();
    }
}
