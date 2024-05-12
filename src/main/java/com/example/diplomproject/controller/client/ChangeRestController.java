package com.example.diplomproject.controller.client;

import com.example.diplomproject.message.AnswerMessage;
import com.example.diplomproject.model.dto.ApplicationForStorageDTO;
import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.repository.ApplicationForReleaseRepository;
import com.example.diplomproject.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
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
@Slf4j
@RequestMapping("/client")
public class ChangeRestController {

    private final IndividualsService individualsService;
    private final TruckService truckService;
    private final ApplicationForMarkingService applicationForMarkingService;
    private final DeclarationTDService declarationTDService;
    private final ProductService productService;
    private final ApplicationForReleaseService applicationForReleaseService;
    private final ApplicationForStorageService applicationForStorageService;
    private final GoodTransportDocumentService goodTransportDocumentService;

    @GetMapping("/findTruck/{id}")
    public TruckDTO getTruckDTO(@PathVariable("id") Long id,
                                Authentication authentication,
                                Model model) {
        return truckService.getTruck(id);
    }

    @PostMapping("/changeAuto/{idAuto}")
    private ResponseEntity<Map<String, String>> changeAuto(@PathVariable Long idAuto, @ModelAttribute @Valid TruckDTO truckDTO,
                                                           BindingResult result,
                                                           Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(truckService.check(result, truckDTO)));
        }
        truckService.changeTruck(truckDTO, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Авто успешно перерегистрированно"));
    }

    @GetMapping("/findSupplier/{id}")
    public IndividualsDTO getProvider(@PathVariable("id") Long id,
                                      Authentication authentication,
                                      Model model) {
        IndividualsDTO individuals = individualsService.findById(id);
        return individuals;
    }

    @PostMapping("/changeSupplier/{idSup}")
    private ResponseEntity<Map<String, String>> changeCompany(@PathVariable Long idSup, @ModelAttribute @Valid IndividualsDTO individualsDTO,
                                                              BindingResult result,
                                                              Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(individualsService.check(result, individualsDTO)));
        }
        individualsService.change(individualsDTO, idSup);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Поставщик успешно изменен"));
    }
    @GetMapping("/deleteZavForMark/{id}")
    private ResponseEntity<Map<String, String>> deleteZavForMark(@PathVariable Long id){
        applicationForMarkingService.deleteApplication(id);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Заяка успешно отменена"));
    }
    @GetMapping("/findDeclaration/{declarationId}")
    private List<Product> findApplication(@PathVariable Long declarationId){
        return declarationTDService.findAllByDeclarationId(declarationId);
    }
    @PostMapping("/regProduct/{id}")
    private ResponseEntity<Map<String, String>> regProduct(@RequestBody List<Product> productList, @PathVariable Long id){
        productService.updateProduct(productList, id);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Товар успешно изменен"));
    }
    @GetMapping("/deleteZavForOtp/{id}")
    private ResponseEntity<Map<String, String>> deleteZavForOtp(@PathVariable Long id){
        applicationForReleaseService.deleteApplication(id);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Заяка успешно отменена"));
    }
    @GetMapping("/findApplicationForStorage/{id}")
    private ApplicationForStorageDTO findApplicationForStorage(@PathVariable Long id){
        return applicationForStorageService.findAllById(id).build();
    }
    @GetMapping("/findProduct/{id}")
    private ProductDTO findProduct(@PathVariable Long id){
        return productService.findById(id);
    }


}
