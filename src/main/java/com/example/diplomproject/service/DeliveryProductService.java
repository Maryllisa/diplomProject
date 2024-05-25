package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.DeliveryProductDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.enumStatus.TypeEvaluation;
import com.example.diplomproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryProductService {
    private final EntityManager entityManager;private final DeliveryProductRepository deliveryProductRepository;
    private final ApplicationForStorageRepository applicationForStorageRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CustomsAgencyRepository customsAgencyRepository;
    private final MarkForAgencyRepository markForAgencyRepository;

    public Map<String, String> check(BindingResult result,
                                     DeliveryProductDTO deliveryProductDTO) {
        Map<String, String> descriptionError = new HashMap<>();
        result.getFieldErrors().forEach(x->{
            switch (x.getField()){
                case "weightProduct":{
                    descriptionError.put("weightProduct", "Некорректно заполнено поле с весом");
                    break;
                }
                case "arrangeDate":{
                    descriptionError.put("arrangeDate", "Некорректно заполнено поле с датой отгрузки");
                    break;
                }
                case "prodCondition":{
                    descriptionError.put("prodCondition", "Некорректно заполнено поле с состоянием товар");
                    break;
                }
                case "deliveryEvalution":{
                    descriptionError.put("deliveryEvalution", "Некорректно заполнено поле оценки отгрузки");
                    break;
                }
                case "idApplicationForStorage":{
                    descriptionError.put("idApplicationForStorage", "Некорректно заполнено поле с номером заявки");
                    break;
                }
            }
        });
        return descriptionError;
    }
    public void addNewDelivery(DeliveryProductDTO deliveryProductDTO, String login){
        DeliveryProduct product = new DeliveryProduct();
        List<Product> productsList = new ArrayList<>();
        deliveryProductDTO.getCheckProduct().forEach((x,y)->{
            Product p = productRepository.getById(x);
            p.setIsDelivery(y);
            p.setApplicationForStorage(product.getApplicationForStorage());
            productsList.add(productRepository.save(p));

        });
        product.setProductList(productsList);
        product.setApplicationForStorage(applicationForStorageRepository.getById(deliveryProductDTO.getIdApplicationForStorage()));
        product.getApplicationForStorage().setStatusApplication(StatusApplication.COMPLETED);
        product.setWeightProduct(deliveryProductDTO.getWeightProduct());
        product.setDeliveryEvalution(deliveryProductDTO.getDeliveryEvalution());
        product.setArrangeDate(deliveryProductDTO.getArrangeDate());
        product.setProdCondition(deliveryProductDTO.getProdCondition());
        product.setAccount(userRepository.findByLogin(login));
        DeliveryProduct delivery = deliveryProductRepository.save(product);
        CustomsAgency agency = new CustomsAgency();
        agency.setDeliveryProduct(delivery);
        agency= customsAgencyRepository.save(agency);
        MarkForAgency mark = new MarkForAgency();
        mark.setCustomsAgency(agency);
        mark.setEvaluation(deliveryProductDTO.getDeliveryEvalution());
        mark.setTypeEvaluation(TypeEvaluation.qualityProduct);
        agency.setListMarkForAgency(markForAgencyRepository.save(mark));
        customsAgencyRepository.save(agency);

    }

    public List<DeliveryProduct> getAllShipment(String name) {
        return deliveryProductRepository.findAllByAccount(userRepository.findByLogin(name));
    }

    public String deleteShipment(Long id) {
        DeliveryProduct deliveryProduct =deliveryProductRepository.getById(id);
        ApplicationForStorage applicationForStorage = applicationForStorageRepository.getById(deliveryProduct.getApplicationForStorage().getIdApplication());
        applicationForStorage.setStatusApplication(StatusApplication.PROCESSING);
        applicationForStorageRepository.save(applicationForStorage);
        deliveryProductRepository.delete(deliveryProduct);
        return "Удаление записи";
    }

    public DeliveryProduct getById(Long id) {
        return deliveryProductRepository.getById(id);
    }

    public List<DeliveryProduct> getAllShipmentByClient(String name) {
        return deliveryProductRepository.findAllByClient(userRepository.findByLogin(name));
    }

    public List<DeliveryProduct> getAllShipment(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<DeliveryProduct> query = builder.createQuery(DeliveryProduct.class);
        Root<DeliveryProduct> root = query.from(DeliveryProduct.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "arrangeDate":
                        orders.add(builder.asc(root.get("arrangeDate")));
                        break;
                    case "prodCondition":
                        orders.add(builder.asc(root.get("prodCondition")));
                        break;
                    case "weightProduct":
                        orders.add(builder.asc(root.get("weightProduct")));
                        break;
                    case "applicationForStorage.declarationTD.productList.productName":
                        orders.add(builder.asc(root.get("applicationForStorage").get("declarationTD").get("productList").get("productName")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "arrangeDate":
                        orders.add(builder.desc(root.get("arrangeDate")));
                        break;
                    case "prodCondition":
                        orders.add(builder.desc(root.get("prodCondition")));
                        break;
                    case "weightProduct":
                        orders.add(builder.desc(root.get("weightProduct")));
                        break;
                    case "applicationForStorage.declarationTD.productList.productName":
                        orders.add(builder.desc(root.get("applicationForStorage").get("declarationTD").get("productList").get("productName")));
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
                case "prodCondition":
                    predicates.add(builder.like(root.get("prodCondition"), searchData.getSearchQuery()));
                    break;
                case "weightProduct":
                    predicates.add(builder.like(root.get("weightProduct"), searchData.getSearchQuery()));
                    break;
                case "applicationForStorage.declarationTD.productList.productName":
                    predicates.add(builder.like(root.get("applicationForStorage").get("declarationTD").get("productList").get("productName"), searchData.getSearchQuery()));
                    break;
            }
        }

        if (searchData.getDateFrom() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("arrangeDate"), searchData.getDateFrom()));
        }

        if (searchData.getDateTo() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("arrangeDate"), searchData.getDateTo()));
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);

        TypedQuery<DeliveryProduct> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<DeliveryProduct> getAllShipmentByClient(String name, SearchData searchData) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<DeliveryProduct> query = builder.createQuery(DeliveryProduct.class);
        Root<DeliveryProduct> root = query.from(DeliveryProduct.class);
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
        TypedQuery<DeliveryProduct> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
