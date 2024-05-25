package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.repository.DriverRepository;
import com.example.diplomproject.repository.IndividualsRepository;
import com.example.diplomproject.repository.TruckRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class TruckService {

    private final TruckRepository trackRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final IndividualsRepository individualsRepository;
    private final EntityManager entityManager;

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

    public Map<String, String> check(BindingResult result, TruckDTO truckDTO) {
        Map<String, String> resultDescription = new HashMap<>();
        result.getFieldErrors().forEach(x->{
            switch (x.getField()){
                case "registrationNumber":{
                    resultDescription.put("registrationNumber","Ошибка при заполнении регистрационного номера авто!");
                }
                case "model":{
                    resultDescription.put("model","Ошибка при заполнении модели авто");
                }
                case "yearTruck":{
                    resultDescription.put("yearTruck","Ошибка при заполнении года");
                }
                case "name":{
                    resultDescription.put("driver.name"," Ошибка при заполнении ФИО водителя");
                }
                case "LicenseNumber":{
                    resultDescription.put("driver.LicenseNumber","Ошибка при заполнении номера лицензии");
                }
            }
        });
        return resultDescription;
    }

    public TruckDTO getTruck(Long id) {
        return trackRepository.getById(id).build();
    }

    public void changeTruck(TruckDTO truckDTO, String name) {
    }

    public List<TruckDTO> getTruck(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Truck> query = builder.createQuery(Truck.class);
        Root<Truck> root = query.from(Truck.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "registrationNumber":
                        orders.add(builder.asc(root.get("registrationNumber")));
                        break;
                    case "brand":
                        orders.add(builder.asc(root.get("brand")));
                        break;
                    case "model":
                        orders.add(builder.asc(root.get("model")));
                        break;
                    case "driver.name":
                        orders.add(builder.asc(root.get("driver").get("name")));
                        break;
                    case "driver.licenseNumber":
                        orders.add(builder.asc(root.get("driver").get("licenseNumber")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "registrationNumber":
                        orders.add(builder.desc(root.get("registrationNumber")));
                        break;
                    case "brand":
                        orders.add(builder.desc(root.get("brand")));
                        break;
                    case "model":
                        orders.add(builder.desc(root.get("model")));
                        break;
                    case "driver.name":
                        orders.add(builder.desc(root.get("driver").get("name")));
                        break;
                    case "driver.licenseNumber":
                        orders.add(builder.desc(root.get("driver").get("licenseNumber")));
                        break;
                }
            }
        }

        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
            switch (searchData.getSearchParam()) {
                case "registrationNumber":
                    predicates.add(builder.like(root.get("registrationNumber"), searchData.getSearchQuery()));
                    break;
                case "brand":
                    predicates.add(builder.like(root.get("brand"), searchData.getSearchQuery()));
                    break;
                case "model":
                    predicates.add(builder.like(root.get("model"), searchData.getSearchQuery()));
                    break;
                case "driver.name":
                    predicates.add(builder.like(root.get("driver.name"), searchData.getSearchQuery()));
                    break;
                case "driver.licenseNumber":
                    predicates.add(builder.like(root.get("driver.licenseNumber"), searchData.getSearchQuery()));
                    break;
             
            }
        }
        
        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        TypedQuery<Truck> typedQuery = entityManager.createQuery(query);
        List<TruckDTO> truckDTOS = new ArrayList<>();
        typedQuery.getResultList().forEach(x->{
            truckDTOS.add(x.build());
        });
        return truckDTOS;
    }
}
