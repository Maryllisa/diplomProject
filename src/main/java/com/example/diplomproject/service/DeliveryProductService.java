package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeliveryProductDTO;
import com.example.diplomproject.model.entity.*;
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
    private final DeliveryProductRepository deliveryProductRepository;
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
}
