package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeliveryProductDTO;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.DeliveryProduct;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.repository.ApplicationForReleaseRepository;
import com.example.diplomproject.repository.ApplicationForStorageRepository;
import com.example.diplomproject.repository.DeliveryProductRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeliveryProductService {
    private final DeliveryProductRepository deliveryProductRepository;
    private final ApplicationForStorageRepository applicationForStorageRepository;
    private final UserRepository userRepository;

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
        product.setCheckProduct(deliveryProductDTO.getCheckProduct());
        product.setApplicationForStorage(applicationForStorageRepository.getById(deliveryProductDTO.getIdApplicationForStorage()));
        product.getApplicationForStorage().setStatusApplication(StatusApplication.COMPLETED);
        product.setApplicationForStorage(
                applicationForStorageRepository
                        .save(product.getApplicationForStorage()));
        product.setWeightProduct(deliveryProductDTO.getWeightProduct());
        product.setDeliveryEvalution(deliveryProductDTO.getDeliveryEvalution());
        product.setArrangeDate(deliveryProductDTO.getArrangeDate());
        product.setProdCondition(deliveryProductDTO.getProdCondition());
        product.setAccount(userRepository.findByLogin(login));
        deliveryProductRepository.save(product);
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
}
