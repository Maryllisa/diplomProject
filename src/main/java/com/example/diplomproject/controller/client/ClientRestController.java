package com.example.diplomproject.controller.client;

import com.example.diplomproject.message.AnswerMessage;
import com.example.diplomproject.model.dto.*;
import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.model.entity.Individuals;
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
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/client")
public class ClientRestController {
    private final DeclarationTDService declarationTDService;
    private final CRMService crmService;
    private final GoodTransportDocumentService goodTransportDocumentService;
    private final IndividualsService individualsService;
    private final TruckService truckService;
    private final ApplicationForStorageService applicationForStorageService;
    private final ApplicationForMarkingService applicationForMarkingService;
    private final ApplicationForReleaseService applicationForReleaseService;

    @SneakyThrows
    @PostMapping("/regOfDeclaration")
    private ResponseEntity<Map<String, String>> checkAddNewDeclaration(@Valid @ModelAttribute DeclarationDTO declarationDTO,
                                                                       BindingResult result,
                                                                       Model model, Authentication authentication,
                                                                       HttpSession session) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(declarationTDService.checkNewDeclaration(result, declarationDTO)));
        }
        List<ProductDTO> productDTOList = (List<ProductDTO>) session.getAttribute("productDTOList");
        log.info("Список из сессии: " + productDTOList);
        declarationDTO.setProductDTOS(productDTOList);
        declarationTDService.addNewDeclaration(declarationDTO, authentication.getName());

        return ResponseEntity.ok(AnswerMessage.getOKMessage("Регистрация декларации " +
                declarationDTO.getCustomEDCode() + "/" +
                declarationDTO.getDirectionOfMovement() + "/" +
                declarationDTO.getProcedureCode() + " успешно пройдена"));

    }

    @SneakyThrows
    @PostMapping("/addCRM")
    private ResponseEntity<Map<String, String>> checkAndAddCRM(@ModelAttribute @Valid CRMDTO crmdto,
                                                               BindingResult result,
                                                               Model model,
                                                               Authentication authentication,
                                                               HttpSession session) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(crmService.checkNewCRM(result, crmdto)));
        }
        log.info("Запуск регистрации нового CRM документа");
        crmService.addNewCRM(crmdto, authentication.getName());

        return ResponseEntity.ok(AnswerMessage.getOKMessage("Регистрация CRM прошла успешно"));

    }

    @PostMapping("/registrationProduct")
    private ResponseEntity<String> addNewProduct(@RequestBody List<ProductDTO> productDTOList, HttpSession session) {
        session.setAttribute("productDTOList", productDTOList);
        return ResponseEntity.ok("Товары добавленны");
    }

    @PostMapping("/addTTN")
    private ResponseEntity<Map<String, String>> addNewGTD(@ModelAttribute GoodTransportDocument goodTransportDocument,
                                                          @RequestParam("pdfFile") MultipartFile file,
                                                          Authentication authentication) {

        goodTransportDocumentService.addNewGTD(goodTransportDocument, file, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("ТТН " + goodTransportDocument.getGoodsTransportDocumentNumbers()
                + " успешно добавлен"));
    }

    @PostMapping("/regAsAComp")
    private ResponseEntity<Map<String, String>> addNewReqAsCompany(@ModelAttribute @Valid IndividualsDTO individualsDTO,
                                                                   BindingResult result,
                                                                   Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(individualsService.check(result, individualsDTO)));
        }
        individualsService.addNewCompany(individualsDTO, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Поставщик успешно добавлен"));
    }

    @PostMapping("/addStorageRequest")
    private ResponseEntity<Map<String, String>> addStorageRequest(@ModelAttribute @Valid ApplicationForStorageDTO applicationForStorageDTO,
                                                                  BindingResult result,
                                                                  Authentication authentication) {

        if (result.hasErrors()) {
            ObjectMapper objectMapper = new ObjectMapper();
//            String body = objectMapper.writeValueAsString(CRMService.checkNewCRM(result, crmdto));
//           return ResponseEntity.badRequest().body(AnswerMessage.getBadMessage(body));
        }
        log.info("РЕГИСТРАЦИЯ НОВОГО ЗАЯВЛЕНИЯ НА ХРАНЕНИЕ");
        applicationForStorageService.addNewApplication(applicationForStorageDTO, authentication.getName());

        return ResponseEntity.ok(AnswerMessage.getOKMessage("Заявка на хранение успешнр зарегистрированна"));
    }

    @PostMapping("/regAuto")
    private ResponseEntity<Map<String, String>> addNewAuto(@ModelAttribute @Valid TruckDTO truckDTO,
                                                           BindingResult result,
                                                           Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(truckService.check(result, truckDTO)));
        }
        truckService.addNewTruck(truckDTO, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Авто успешно зарегистрированно"));
    }

    @GetMapping("/findSupplier/{id}")
    public IndividualsDTO getProvider(@PathVariable("id") Long id,
                                      Authentication authentication,
                                      Model model) {
        IndividualsDTO individuals = individualsService.findById(id);
        return individuals;
    }
    @PostMapping("/changeSupplier/{idSup}")
    private ResponseEntity<Map<String, String>> changeCompany(@PathVariable Long idSup,@ModelAttribute @Valid IndividualsDTO individualsDTO,
                                                                   BindingResult result,
                                                                   Authentication authentication) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    AnswerMessage.getBadMessage(individualsService.check(result, individualsDTO)));
        }
        individualsService.change(individualsDTO, idSup);
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Поставщик успешно изменен"));
    }

    @PostMapping("/addZavForMark")
    public ResponseEntity<Map<String, String>> addApplicationForMarking(@ModelAttribute ApplicationForMarkingDTO applicationForMarkingDTO,
                                                                        Authentication authentication) {
        applicationForMarkingService.addNewApplicationForMarking
                (applicationForMarkingDTO, authentication.getName());
        return ResponseEntity.ok(AnswerMessage.getOKMessage("Заявка на маркировку успешно оформлена"));
    }

    @PostMapping("/maleZavForOtp")
    public ResponseEntity<Map<String, String>> addApplicationForRelease(@ModelAttribute ApplicationForReleaseDTO applicationForReleaseDTO,
                                                                        Authentication authentication) {
        applicationForReleaseService.addNewApplicationForRelease
                (applicationForReleaseDTO, authentication.getName());

        return ResponseEntity.ok(AnswerMessage.getOKMessage("Заявка на хронение умпешно оформлена"));
    }
}
