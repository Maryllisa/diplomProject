package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OtpuskService {
    private final EntityManager entityManager;private final OtpuskRepository otpuskRepository;
    private final ApplicationForReleaseRepository applicationForRelease;
    private final UserRepository userRepository;
    private final DeliveryProductRepository deliveryProductRepository;
    private final CustomsAgencyRepository customsAgencyRepository;

    @SneakyThrows
    public void addOtpusk(Long idApplicationForRelease, double sumForStorage, MultipartFile file) {
        log.info("ДОБАВЛЕНИЕ НОВОЙ ОПЛАТЫ");
        Otpusk otpusk = new Otpusk();
        otpusk.setNameSrc(file.getName());
        otpusk.setSize(file.getSize());
        Base64.Encoder encoder = Base64.getEncoder();
        String encoded = encoder.encodeToString(file.getBytes());
        otpusk.setSrc(encoded);
        log.info("ПОИСК ЗАЯВЛЕНИЕ НА РЕАЛИЗАЦИЮ");
        ApplicationForRelease application =applicationForRelease.getById(idApplicationForRelease);
        application.setStatusApplicationForRelease(StatusApplicationForRelease.PAID);
        otpusk.setApplicationForRelease(application);
        otpusk.setSumForStorage(sumForStorage);
        otpusk = otpuskRepository.save(otpusk);
        DeliveryProduct deliveryProduct = null;
        List<DeliveryProduct> deliveryProducts = deliveryProductRepository.findAll();
        for(DeliveryProduct x : deliveryProducts){
            if (x.getProductList().stream().map(Product::getIdProduct)
                    .anyMatch(y->y.equals(application.getProduct().getIdProduct()))) {
                deliveryProduct = x;
                break;
            }
        }
        CustomsAgency customsAgency = new CustomsAgency();
        if (deliveryProduct!=null) {
            customsAgency = customsAgencyRepository.findByDeliveryProduct(deliveryProduct).orElse(null);
        }
        customsAgency.setOtpusk(otpusk);
        customsAgencyRepository.save(customsAgency);

    }

    public List<Otpusk> getAllByLogin(String name) {
        return otpuskRepository.findAllByApplicationForRelease(userRepository.findByLogin(name));
    }

    public Otpusk getByApplicationForRelease(Long id) {
        return otpuskRepository.findAllByApplicationForRelease(applicationForRelease.getById(id));
    }

    public List<Otpusk> getAllByAccountClient(String name) {
        return otpuskRepository.findAllByClient(userRepository.findByLogin(name));
    }

    public List<Otpusk> getAllByLogin(String name, SearchData searchData) {
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
        TypedQuery<Otpusk> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<Otpusk> getAllByAccountClient(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Otpusk> query = builder.createQuery(Otpusk.class);
        Root<Otpusk> root = query.from(Otpusk.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "idMarkingInfo":
                        orders.add(builder.asc(root.get("idMarkingInfo")));
                        break;
                    case "markForAgency.evaluation":
                        orders.add(builder.asc(root.get("markForAgency").get("evaluation")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "idMarkingInfo":
                        orders.add(builder.desc(root.get("idMarkingInfo")));
                        break;
                    case "markForAgency.evaluation":
                        orders.add(builder.desc(root.get("markForAgency").get("evaluation")));
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
                case "idMarkingInfo":
                    predicates.add(builder.like(root.get("idMarkingInfo"), searchData.getSearchQuery()));
                    break;
                case "markForAgency.evaluation":
                    predicates.add(builder.like(root.get("markForAgency").get("evaluation"), searchData.getSearchQuery()));
                    break;
            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        TypedQuery<Otpusk> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
