package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.ApplicationForReleaseDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationForReleaseService {
    private final EntityManager entityManager;private final ApplicationForReleaseRepository applicationForReleaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DeliveryProductRepository deliveryProductRepository;
    private final CustomsAgencyRepository customsAgencyRepository;

    public List<ApplicationForReleaseDTO> getAllApplicationForRelease(String name) {
        List<ApplicationForRelease> applicationForReleaseList = applicationForReleaseRepository
                .findAllByAccount(userRepository.findByLogin(name));
        List<ApplicationForReleaseDTO> applicationForReleaseDTOList = new ArrayList<>();
        applicationForReleaseList.forEach(x->{
            applicationForReleaseDTOList.add(x.build());
        });
        return applicationForReleaseDTOList;
    }

    public void addNewApplicationForRelease(ApplicationForReleaseDTO applicationForReleaseDTO,
                                            String name) {
        Account account = userRepository.findByLogin(name);
        Product product = productRepository.findById(applicationForReleaseDTO.getIdProduct()).orElse(null);
        ApplicationForRelease applicationForRelease = applicationForReleaseDTO.build(product, StatusApplicationForRelease.IN_PROCESSING);
        applicationForRelease.setAccount(account);
        applicationForRelease.setStatusApplicationForRelease(StatusApplicationForRelease.IN_PROCESSING);
        ApplicationForRelease application = applicationForReleaseRepository.save(applicationForRelease);
        if (product != null) {
            product.setApplicationForRelease(applicationForRelease);
            productRepository.save(product);
        }

    }

    public void deleteApplication(Long id) {
        ApplicationForRelease application = applicationForReleaseRepository.getById(id);
        Product product = productRepository.getById(application.getProduct().getIdProduct());
        product.setApplicationForRelease(null);
        productRepository.save(product);
        applicationForReleaseRepository.delete(application);
    }

    public List<ApplicationForRelease> getAllApplicationForRelease() {
        return applicationForReleaseRepository.findAll();
    }

    public void changeStatus(Long id, StatusApplicationForRelease statusApplicationForRelease) {
        ApplicationForRelease application = applicationForReleaseRepository.getById(id);
        application.setStatusApplicationForRelease(statusApplicationForRelease);

        applicationForReleaseRepository.save(application);
    }

    public List<ApplicationForRelease> getAllApplicationForReleaseAndStatus(StatusApplicationForRelease statusApplicationForRelease, String login) {
        List<ApplicationForRelease> applicationForReleaseList = applicationForReleaseRepository.findAllByStatusApplicationForReleaseAndAccount(statusApplicationForRelease, userRepository.findByLogin(login));
        applicationForReleaseList.addAll(applicationForReleaseRepository.findAllByStatusApplicationForReleaseAndAccount(StatusApplicationForRelease.PAID, userRepository.findByLogin(login)));
        return applicationForReleaseList;
    }

    public ApplicationForRelease getApplicationForReleaseById(Long id) {
        return applicationForReleaseRepository.getById(id);
    }

    public List<ApplicationForRelease> getAllApplicationForReleaseAndStatus(String login, StatusApplicationForRelease statusApplicationForRelease) {
        return applicationForReleaseRepository.findAllByStatusApplicationForRelease(statusApplicationForRelease);
    }

    public List<ApplicationForRelease> getAllApplicationForReleaseAndStatus(String name,
                                                                            StatusApplicationForRelease statusApplicationForRelease,
                                                                            SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ApplicationForRelease> query = builder.createQuery(ApplicationForRelease.class);
        Root<ApplicationForRelease> root = query.from(ApplicationForRelease.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "date":
                        orders.add(builder.asc(root.get("date")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.asc(root.get("product").get("nameProduct")));
                        break;
                    case "product.grossWeight":
                        orders.add(builder.asc(root.get("product").get("grossWeight")));
                        break;
                    case "product.netWeight":
                        orders.add(builder.asc(root.get("product").get("netWeight")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "date":
                        orders.add(builder.desc(root.get("date")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.desc(root.get("product").get("nameProduct")));
                        break;
                    case "product.grossWeight":
                        orders.add(builder.desc(root.get("product").get("grossWeight")));
                        break;
                    case "product.netWeight":
                        orders.add(builder.desc(root.get("product").get("netWeight")));
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
                case "product.nameProduct":
                    predicates.add(builder.like(root.get("product").get("nameProduct"), searchData.getSearchQuery()));
                    break;
                case "product.grossWeight":
                    predicates.add(builder.like(root.get("product").get("grossWeight"), searchData.getSearchQuery()));
                    break;
                case "product.netWeight":
                    predicates.add(builder.like(root.get("product").get("netWeight"), searchData.getSearchQuery()));
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
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("statusApplicationForRelease"), statusApplicationForRelease));
        query.where(searchPredicate);

        TypedQuery<ApplicationForRelease> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<ApplicationForRelease>  getAllApplicationForRelease(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<ApplicationForRelease> query = builder.createQuery(ApplicationForRelease.class);
        Root<ApplicationForRelease> root = query.from(ApplicationForRelease.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "idApplicationForRelease":
                        orders.add(builder.asc(root.get("idApplication")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.asc(root.get("product").get("nameProduct")));
                        break;
                    case "statusApplicationForRelease":
                        orders.add(builder.asc(root.get("statusApplicationForRelease")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "idApplicationForRelease":
                        orders.add(builder.desc(root.get("idApplication")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.desc(root.get("product").get("nameProduct")));
                        break;
                    case "statusApplicationForRelease":
                        orders.add(builder.desc(root.get("statusApplicationForRelease")));
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
                case "idApplicationForRelease":
                    predicates.add(builder.like(root.get("idApplication"), searchData.getSearchQuery()));
                    break;
                case "statusApplicationForRelease":
                    predicates.add(builder.like(root.get("statusApplicationForRelease"), searchData.getSearchQuery()));
                    break;
                case "product.nameProduct":
                    predicates.add(builder.like(root.get("product").get("nameProduct"), searchData.getSearchQuery()));
                    break;
            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        TypedQuery<ApplicationForRelease> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();

    }

    public Object getAllApplicationForReleaseAndStatus(StatusApplicationForRelease statusApplicationForRelease, String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Otpusk> query = builder.createQuery(Otpusk.class);
        Root<Otpusk> root = query.from(Otpusk.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "id":
                        orders.add(builder.asc(root.get("idApplication")));
                        break;
                    case "applicationForRelease.product.productCode":
                        orders.add(builder.asc(root.get("applicationForRelease").get("product").get("productCode")));
                        break;
                    case "applicationForRelease.date":
                        orders.add(builder.asc(root.get("applicationForRelease").get("date")));
                        break;
                    case "sumForStorage":
                        orders.add(builder.asc(root.get("sumForStorage").get("sumForStorage")));
                        break;

                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "id":
                        orders.add(builder.desc(root.get("idApplication")));
                        break;
                    case "applicationForRelease.product.productCode":
                        orders.add(builder.desc(root.get("applicationForRelease").get("product").get("productCode")));
                        break;
                    case "applicationForRelease.date":
                        orders.add(builder.desc(root.get("applicationForRelease").get("date")));
                        break;
                    case "sumForStorage":
                        orders.add(builder.desc(root.get("sumForStorage").get("sumForStorage")));
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
                case "id":
                    predicates.add(builder.like(root.get("idApplication"), searchData.getSearchQuery()));
                    break;
                case "applicationForRelease.product.productCode":
                    predicates.add(builder.like(root.get("applicationForRelease").get("product").get("productCode"), searchData.getSearchQuery()));
                    break;
                case "sumForStorage":
                    predicates.add(builder.like(root.get("sumForStorage"), searchData.getSearchQuery()));
                    break;
            }
        }

        if (searchData.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("applicationForRelease").get("date"), searchData.getDateFrom()));
        }

        if (searchData.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("applicationForRelease").get("date"), searchData.getDateTo()));
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("applicationForRelease").get("statusApplicationForRelease"), statusApplicationForRelease));
        query.where(searchPredicate);
        TypedQuery<Otpusk> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
