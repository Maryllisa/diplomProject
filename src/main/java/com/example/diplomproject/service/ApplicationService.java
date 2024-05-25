package com.example.diplomproject.service;

import javax.persistence.EntityManager;

import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.repository.ApplicationRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final EntityManager entityManager;
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

    public List<ApplicationForStorage> getApplicationTrue(StatusApplication statusApplication, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ApplicationForStorage> query = builder.createQuery(ApplicationForStorage.class);
        Root<ApplicationForStorage> root = query.from(ApplicationForStorage.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "countPositionProducts":
                        orders.add(builder.asc(root.get("countPositionProducts")));
                        break;
                    case "datePost":
                        orders.add(builder.asc(root.get("datePost")));
                        break;
                    case "dateZav":
                        orders.add(builder.asc(root.get("dateZav")));
                        break;
                    case "statusApplication":
                        orders.add(builder.asc(root.get("statusApplication")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "countPositionProducts":
                        orders.add(builder.desc(root.get("countPositionProducts")));
                        break;
                    case "datePost":
                        orders.add(builder.desc(root.get("datePost")));
                        break;
                    case "dateZav":
                        orders.add(builder.desc(root.get("dateZav")));
                        break;
                    case "statusApplication":
                        orders.add(builder.desc(root.get("statusApplication")));
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
                case "countPositionProducts":
                    predicates.add(builder.like(root.get("countPositionProducts"), searchData.getSearchQuery()));
                    break;
                case "datePost":
                    predicates.add(builder.like(root.get("datePost"), searchData.getSearchQuery()));
                    break;
                case "dateZav":
                    predicates.add(builder.like(root.get("dateZav"), searchData.getSearchQuery()));
                    break;
                case "statusApplication":
                    predicates.add(builder.like(root.get("statusApplication"), searchData.getSearchQuery()));
                    break;
            }
        }

        if (searchData.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("datePost"), searchData.getDateFrom()));
        }

        if (searchData.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("datePost"), searchData.getDateTo()));
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.like(root.get("statusApplication"), statusApplication));
        query.where(searchPredicate);

        TypedQuery<ApplicationForStorage> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
