package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.repository.ApplicationForMarkingRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationForMarkingService {
    private final EntityManager entityManager;
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

    public List<ApplicationForMarkingDTO> getAllApplicationsForMarking() {
        List<ApplicationForMarking> applicationForMarking = applicationForMarkingRepository.findAllByStatusMarkingApplication(StatusMarkingApplication.PENDING);
        List<ApplicationForMarkingDTO> applicationForMarkingDTOList = new ArrayList<>();
        applicationForMarking.forEach(x->{
            applicationForMarkingDTOList.add(x.build());
        });
        return applicationForMarkingDTOList;
    }

    public void deleteApplication(Long id) {
        applicationForMarkingRepository.delete(applicationForMarkingRepository.getById(id));
    }


    public String changeStatus(Long idMark, StatusMarkingApplication statusApplication) {
        ApplicationForMarking application = applicationForMarkingRepository.getById(idMark);
        application.setStatusMarkingApplication(statusApplication);
        applicationForMarkingRepository.save(application);
        return "УСПЕШНО";
    }

    public List<ApplicationForMarkingDTO> getAllApplicationsForMarking(SearchData searchData) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ApplicationForMarking> query = builder.createQuery(ApplicationForMarking.class);
        Root<ApplicationForMarking> root = query.from(ApplicationForMarking.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "product.idProduct":
                        orders.add(builder.asc(root.get("product").get("idProduct")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.asc(root.get("product").get("nameProduct")));
                        break;
                    case "date":
                        orders.add(builder.asc(root.get("date")));
                        break;
                }
            }
            else {
                switch (searchData.getSortCriteria()) {
                    case "product.idProduct":
                        orders.add(builder.desc(root.get("product").get("idProduct")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.desc(root.get("product").get("nameProduct")));
                        break;
                    case "date":
                        orders.add(builder.desc(root.get("date")));
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
                case "product.idProduct":
                    predicates.add(builder.equal(root.get("product").get("idProduct"), searchData.getSearchQuery()));
                    break;
                case "product.nameProduct":
                    predicates.add(builder.equal(root.get("product").get("nameProduct"), searchData.getSearchQuery()));
                    break;
            }
        }

        if (searchData.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("date"), searchData.getDateFrom()));
        }

        if (searchData.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("date"), searchData.getDateTo()));
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        query.where(searchPredicate);

        TypedQuery<ApplicationForMarking> typedQuery = entityManager.createQuery(query);
        List<ApplicationForMarking> applicationForMarking =typedQuery.getResultList();
        List<ApplicationForMarkingDTO> applicationForMarkingDTOList = new ArrayList<>();
        applicationForMarking.forEach(x->{
            applicationForMarkingDTOList.add(x.build());
        });
        return applicationForMarkingDTOList;
    }
}
