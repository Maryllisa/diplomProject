package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.repository.DriverRepository;
import com.example.diplomproject.repository.IndividualsRepository;
import com.example.diplomproject.repository.TruckRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TruckService {

    private final TruckRepository trackRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final IndividualsRepository individualsRepository;

    public void addNewTruck(TruckDTO truckDTO, String name) {
        Truck truck = truckDTO.build();
        Account account = userRepository.findByLogin(name);
        Individuals individuals = userRepository.findAllIndividualsByAccount(account).get(0) != null ? userRepository.findAllIndividualsByAccount(account).get(0) : null;
        if (individuals == null){
            individuals = new Individuals();
            individuals.setAccount(account);
            individualsRepository.save(individuals);
        }
        truck.setDriver(driverRepository.save(truck.getDriver()));
        truck.setAccount(account);
        trackRepository.save(truck);
    }
    public List<TruckDTO> getTruck(String login) {
        List<TruckDTO> truckDTOS = new ArrayList<>();
        trackRepository.findAllByAccount(userRepository.findByLogin(login)).forEach(x->{
            truckDTOS.add(x.build());
        });
        return truckDTOS;
    }
}