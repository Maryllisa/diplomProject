package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Driver;
import com.example.diplomproject.model.entity.Truck;
import com.example.diplomproject.repository.DriverRepository;
import com.example.diplomproject.repository.TruckRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TruckService {

    private final TruckRepository trackRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public void addNewTruck(TruckDTO truckDTO, String name) {
        Truck truck = truckDTO.build();
        Account account = userRepository.findByLogin(name);
        truck.setAccount(account);
        truck.setDriver(driverRepository.save(truck.getDriver()));
        trackRepository.save(truck);

    }
}
