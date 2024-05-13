package com.example.diplomproject.controller.manager;

import com.example.diplomproject.message.AnswerMessage;
import com.example.diplomproject.model.dto.DeliveryProductDTO;
import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.DeliveryProduct;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.model.entity.marking.StatusMarkingApplication;
import com.example.diplomproject.service.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final MarkingInfoService markingInfoService;
    private final ApplicationForStorageService applicationForStorageService;
    private final ApplicationForMarkingService applicationForMarking;
    private final ProductService productService;
    private final DeliveryProductService deliveryProductService;
    private final ApplicationForReleaseService applicationForReleaseService;
    @PostMapping("/registration/marking")
    public ResponseEntity<String> addNewMarking(@ModelAttribute MarkingInfoDTO markingInfoDTO,
                                                Model model){
        markingInfoService.addNewMarking(markingInfoDTO);
        return ResponseEntity.ok("Добавлено");
    }
    @GetMapping("/qr/{id}")
    private ResponseEntity<?> getImageByIDUser(@PathVariable Long id)
    {
        MarkingInfo qr = markingInfoService.findById(id);
        Base64.Decoder decoder = Base64.getDecoder();
        return ResponseEntity.ok()
                .header("fileName", qr.getOriginalFileName())
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(Long.parseLong(qr.getSize()))
                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcCode()))));
    }
//    @GetMapping("/client/qr/{id}")
//    private ResponseEntity<?> getImageByIDClient(@PathVariable String id)
//    {
//        MarkingInfo qr = markingInfoService.findById(Integer.parseInt(id));
//        Base64.Decoder decoder = Base64.getDecoder();
//        return ResponseEntity.ok()
//                .header("fileName", qr.getOriginalFileName())
//                .contentType(MediaType.IMAGE_PNG)
//                .contentLength(Long.parseLong(qr.getSize()))
//                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrcCode()))));
//    }

    @GetMapping("/productsInfo/{idProduct}")
    private ResponseEntity<MarkingInfoDTO> getMarkInfo(@PathVariable Long idProduct){
        return ResponseEntity.ok(markingInfoService.findById(idProduct).build());
    }
    @GetMapping("/findApplication/{id}")
    private ApplicationForStorage getMarkInfos(@PathVariable Long id){
        return applicationForStorageService.findById(id);
    }
    @GetMapping("/changeStatusMark/{idMark}")
    public ResponseEntity<String> changeStatusMark(@PathVariable Long idMark){
        return ResponseEntity.ok(applicationForMarking.changeStatus(idMark, StatusMarkingApplication.CANCELED));
    }
    @GetMapping("/findProductByApplication/{id}")
    public List<Product> getProductByApplication(@PathVariable Long id){
        return productService.getAllProductByApplication(id);
    }
    @PostMapping("/addDelivery")
    public ResponseEntity<Map<String, String>> addDelivery(@RequestBody @Valid DeliveryProductDTO deliveryProductDTO,
                                                           BindingResult result, Authentication authentication){
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(deliveryProductService.check(result,deliveryProductDTO)));
        }
        deliveryProductService.addNewDelivery(deliveryProductDTO, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Отгрузка успешно оформлена"));
    }
    @GetMapping("/deleteShipment/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable Long id){
        return ResponseEntity.ok(deliveryProductService.deleteShipment(id));
    }
    @GetMapping("/findAllInfoProduct/{id}")
    public DeliveryProduct getDeliveryById(Model model, Authentication authentication, @PathVariable Long id) {
        return deliveryProductService.getById(id);
    }
    @GetMapping("/changeStatusApplicationForRelease/{id}")
    public ResponseEntity<String> changeStatusApplicationForRelease(@PathVariable Long id){
        applicationForReleaseService.changeStatus(id, StatusApplicationForRelease.AWAITING_PAYMENT);
        return ResponseEntity.ok("");
    }
}
