package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.chat.ChatRoom;
import com.example.diplomproject.model.entity.marking.ApplicationForMarking;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.repository.*;
import com.example.diplomproject.service.mark.BarcodeService;
import com.example.diplomproject.service.mark.DataMatrixCodeService;
import com.example.diplomproject.service.mark.QRCodeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MarkingInfoService {
    private final EntityManager entityManager;private final MarkingInfoRepository markingInfoRepository;
    private final ProductRepository productRepository;
    private final QRCodeService qrCodeService;
    private final DataMatrixCodeService dataMatrixCodeService;
    private final BarcodeService barcodeService;
    private final ApplicationForMarkingRepository applicationForMarkingRepository;
    private final UserRepository userRepository;
    private final CustomsAgencyRepository customsAgencyRepository;
    private final DeliveryProductRepository deliveryProductRepository;

    @SneakyThrows
    public void addNewMarking(MarkingInfoDTO markingInfoDTO) {
        Product product = productRepository.findById(markingInfoDTO.getIdProduct()).orElse(null);
        ApplicationForMarking applicationForMarking = applicationForMarkingRepository.findAllByProduct(product);
        markingInfoDTO.setProduct(product.build());
        MarkingInfo markingInfo = markingInfoDTO.build();
        markingInfo.setDatePut(Date.valueOf(LocalDate.now()));
        markingInfo.setApplicationForMarking(applicationForMarking);
        if (markingInfoDTO.getTypeMarking().equals(TypeMarking.ACCISE_MARK)){
            MultipartFile file = qrCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);

        } else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.CONTROL_MARK)) {
            MultipartFile file = qrCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.RFID_MARK)) {
            MultipartFile file = qrCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.STICKER)) {
            MultipartFile file = barcodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        else if (markingInfoDTO.getTypeMarking().equals(TypeMarking.DATAMATRIX_CODE)) {
            MultipartFile file = dataMatrixCodeService.generate(product.build());
            markingInfo.addMarking(file);
            markingInfo.setProduct(product);
        }
        markingInfo = markingInfoRepository.save(markingInfo);
        applicationForMarking.setMarkingInfo(markingInfo);
        applicationForMarking.setStatusMarkingApplication(StatusMarkingApplication.COMPLETED);
        DeliveryProduct deliveryProduct = null;
        List<DeliveryProduct> deliveryProducts = deliveryProductRepository.findAll();
        for(DeliveryProduct x : deliveryProducts){
            if (x.getProductList().stream().map(Product::getIdProduct)
                    .anyMatch(y->y.equals(product.getIdProduct()))) {
                deliveryProduct = x;
                break;
            }
        }
        CustomsAgency customsAgency = new CustomsAgency();
        if (deliveryProduct!=null) {
            customsAgency = customsAgencyRepository.findByDeliveryProduct(deliveryProduct).orElse(null);
        }
        customsAgency.setMarkingInfo(markingInfo);
        customsAgencyRepository.save(customsAgency);
        applicationForMarkingRepository.save(applicationForMarking);
    }

    public MarkingInfo findById(Long i) {
        return markingInfoRepository.findById(i).orElse(null);
    }

    public List<MarkingInfo> getAllMarking() {
        return markingInfoRepository.findAll();
    }

    public List<MarkingInfo> getAllMarking(String login) {
        Account account = userRepository.findByLogin(login);
        return markingInfoRepository.findAllByAccount(account);
    }

    public List<MarkingInfo> getAllMarking(SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<MarkingInfo> query = builder.createQuery(MarkingInfo.class);
        Root<MarkingInfo> root = query.from(MarkingInfo.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "product.nameProduct":
                        orders.add(builder.asc(root.get("product").get("nameProduct")));
                        break;
                    case "product.productCode":
                        orders.add(builder.asc(root.get("product").get("productCode")));
                        break;
                    case "product.date":
                        orders.add(builder.asc(root.get("product").get("date")));
                        break;
                    case "product.typeMarking":
                        orders.add(builder.asc(root.get("product").get("typeMarking")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "product.date":
                        orders.add(builder.desc(root.get("product").get("date")));
                        break;
                    case "product.nameProduct":
                        orders.add(builder.desc(root.get("product").get("nameProduct")));
                        break;
                    case "product.productCode":
                        orders.add(builder.desc(root.get("product").get("productCode")));
                        break;
                    case "product.typeMarking":
                        orders.add(builder.desc(root.get("product").get("typeMarking")));
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

        TypedQuery<MarkingInfo> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<MarkingInfo> getAllMarking(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<MarkingInfo> query = builder.createQuery(MarkingInfo.class);
        Root<MarkingInfo> root = query.from(MarkingInfo.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "idMarkingInfo":
                        orders.add(builder.asc(root.get("markForAgency").get("idMarkForAgency")));
                        break;
                    case "markForAgency.evaluation":
                        orders.add(builder.asc(root.get("markForAgency").get("evaluation")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "idMarkingInfo":
                        orders.add(builder.desc(root.get("markForAgency").get("idMarkForAgency")));
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
                    predicates.add(builder.like(root.get("markForAgency").get("idMarkForAgency"), searchData.getSearchQuery()));
                    break;
                case "markForAgency.evaluation":
                    predicates.add(builder.like(root.get("markForAgency").get("evaluation"), searchData.getSearchQuery()));
                    break;
            }
        }

        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
//        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
//        query.where(searchPredicate);
        TypedQuery<MarkingInfo> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
