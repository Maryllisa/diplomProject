package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.*;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ClientRestController {
    private final DeclarationTDService declarationTDService;
    private final CRMService crmService;
    private final GoodTransportDocumentService goodTransportDocumentService;
    private final IndividualsService individualsService;
    private final TruckService truckService;
    private final ApplicationForStorageService applicationForStorageService;

    // Добавить валидацию
    @SneakyThrows
    @PostMapping("/client/regOfDeclaration")
    private ResponseEntity<String> checkAddNewDeclaration(@ModelAttribute DeclarationDTO declarationDTO,
                                                          BindingResult result,
                                                          Model model, Authentication authentication, HttpSession session){

//        if (result.hasErrors()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String body = objectMapper.writeValueAsString(declarationTDService.checkNewDeclaration(result, declarationDTO));
//            return ResponseEntity.badRequest().body(body);
//        }
        List<ProductDTO> productDTOList = (List<ProductDTO>) session.getAttribute("productDTOList");
        log.info("Список из сессии: " + productDTOList);
        declarationDTO.setProductDTOS(productDTOList);
        declarationTDService.addNewDeclaration(declarationDTO, authentication.getName());

        return ResponseEntity.ok("Есть контакт!!");

    }
    @SneakyThrows
    @PostMapping("/client/addCRM")
    private ResponseEntity<String> checkAndAddCRM(@ModelAttribute CRMDTO crmdto,
                                                          BindingResult result,
                                                          Model model,
                                                  Authentication authentication,
                                                  HttpSession session){

        if (result.hasErrors()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(CRMService.checkNewCRM(result, crmdto));
            return ResponseEntity.badRequest().body(body);
        }
        log.info("Запуск регистрации нового CRM документа");
        crmService.addNewCRM(crmdto, authentication.getName());

        return ResponseEntity.ok("Есть контакт!!");

    }
    @PostMapping("/client/registrationProduct")
    private ResponseEntity<String> addNewProduct(@RequestBody List<ProductDTO> productDTOList, HttpSession session){
        session.setAttribute("productDTOList", productDTOList);
        return  ResponseEntity.ok("Товары добавленны");
    }
    @PostMapping("/client/addTTN")
    private ResponseEntity<String> addNewGTD(@ModelAttribute GoodTransportDocument goodTransportDocument,
                                             @RequestParam("pdfFile") MultipartFile file,
                                             Authentication authentication){

        goodTransportDocumentService.addNewGTD(goodTransportDocument, file, authentication.getName());
        return  ResponseEntity.ok("ТТН добавлен");
    }
    @PostMapping("/client/regAsAComp")
    private ResponseEntity<String> addNewReqAsCompany(@ModelAttribute IndividualsDTO individualsDTO,
                                                      Authentication authentication){

        individualsService.addNewCompany(individualsDTO, authentication.getName());
        return  ResponseEntity.ok("Успешная регистрация");
    }
    @PostMapping("/client/addStorageRequest")
    private ResponseEntity<String> addStorageRequest(@ModelAttribute ApplicationForStorageDTO applicationForStorageDTO, Authentication authentication){
        applicationForStorageService.addNewApplication(applicationForStorageDTO, authentication.getName());
        return  ResponseEntity.ok("Успешная регистрация");
    }
    @PostMapping("/client/regAuto")
    private ResponseEntity<String> addNewAuto(@ModelAttribute TruckDTO truckDTO, Authentication authentication){
        truckService.addNewTruck(truckDTO, authentication.getName());
        return  ResponseEntity.ok("Успешная регистрация");
    }

}
