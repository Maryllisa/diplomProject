package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.*;
import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.model.entity.enumStatus.Brand;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final DeclarationTDService declarationTDService;
    private final IndividualsService individualsService;
    private final CRMService crmService;
    private final GoodTransportDocumentService goodTransportDocumentService;
    private final TruckService truckService;
    private final ApplicationForMarkingService applicationForMarkingService;
    private final ProductService productService;
    private final ApplicationForReleaseService applicationForRelease;
    private final ApplicationForStorageService applicationForStorage;
    private final MarkingInfoService markingInfoService;
    private final AccountService accountService;
    private final OtpuskService otpuskService;
    private final DeliveryProductService deliveryProductService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/")
    public String getClient(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/client/clientPanel";
    }

    @GetMapping("/regOfDeclaration")
    public String getRegOfDeclaration(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        String login = authentication.getName();
        model.addAttribute("supplier", declarationTDService.getSupplier(login));
        model.addAttribute("declaration", new DeclarationDTO());
        return "/client/regOfDeclaration";
    }

    @GetMapping("/addStorageRequest")
    public String getAddStorageRequest(Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("gpd", goodTransportDocumentService.getAllByAccaount(authentication.getName()));
        model.addAttribute("declaration", declarationTDService.findAllByAccount(authentication.getName()));
        model.addAttribute("crm", crmService.findAllByAccount(authentication.getName()));
        model.addAttribute("truck", truckService.getTruck(authentication.getName()));
        model.addAttribute("application", new ApplicationForStorageDTO());
        return "/client/addStorageRequest";
    }

    @GetMapping("/addTTN")
    public String getAddTTN(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("gtd", new GoodTransportDocument());
        return "/client/addTTN";
    }

    @GetMapping("/zavStatus")
    public String getZavStatus(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationList", applicationForStorage.getAllApplicationByAccount(authentication.getName()));
        return "/client/zavStatus";
    }

    @GetMapping("/prodStatus")
    public String getProdStatus(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("productList", productService.getAllProduct(authentication.getName()));
        return "/client/prodStatus";
    }

    @GetMapping("/addCMR")
    public String getAddCMR(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("supplier", declarationTDService.getSupplier(authentication.getName()));
        model.addAttribute("crm", new CRMDTO());
        return "/client/addCMR";
    }

    @GetMapping("/regAsAComp")
    public String getRegAsAComp(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("supplier", individualsService.getSuppliers(authentication.getName()));
        model.addAttribute("newSupplier", new IndividualsDTO());
        return "/client/regAsAComp";
    }

    @GetMapping("/regAuto")
    public String getRegAuto(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("brand", Brand.getRussianName());
        model.addAttribute("truck", new TruckDTO());
        model.addAttribute("trucks", truckService.getTruck(authentication.getName()));
        return "/client/regAuto";
    }

    @GetMapping("/showMarkedProduct")
    public String getShowMarkedProduct(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("marks", markingInfoService.getAllMarking(authentication.getName()));
        return "/client/showMarkedProduct";
    }

    @GetMapping("/addZavForMark")
    public String getAddZavForMark(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationsForMarking" , applicationForMarkingService.getAllApplicationsForMarking(authentication.getName()));
        model.addAttribute("applicationForMarking", new ApplicationForMarkingDTO());
        model.addAttribute("productList", productService.getAllProduct(authentication.getName()));
        model.addAttribute("typeMarkings", TypeMarking.getRussianName());
        return "/client/addZavForMark";
    }

    @GetMapping("/makeZavForOtp")
    public String getMakeZavForOtp(Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("productList", productService.getAllProductByApplication(authentication.getName()));
        model.addAttribute("applicationForRelease", applicationForRelease.getAllApplicationForRelease(authentication.getName()));
        model.addAttribute("newApplicationForRelease", new ApplicationForReleaseDTO());
        model.addAttribute("StatusEnum", StatusApplicationForRelease.class);
        return "/client/makeZavForOtp";
    }

    @GetMapping("/showBillForSave")
    public String getShowBillForSave(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("oplataList", otpuskService.getAllByLogin(authentication.getName()));
        return "/client/showBillForSave";
    }

    @GetMapping("/priceForSave")
    public String getPriceForSave(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationList", applicationForRelease.getAllApplicationForReleaseAndStatus(StatusApplicationForRelease.AWAITING_PAYMENT, authentication.getName()));
        model.addAttribute("newOtpusk", new Otpusk());
        return "/client/priceForSave";
    }

    @GetMapping("/showListTTN")
    public String getShowListTTN(Authentication authentication, Model model) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("ttn", goodTransportDocumentService.getAllByAccaount(authentication.getName()));
        return "/client/showListTTN";
    }
    @GetMapping("/showListTD")
    public String getShowListTD(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("tdList", declarationTDService.findAllByAccount(authentication.getName()));
        return "/client/showListTD";
    }
    @GetMapping("/showTD/{id}")
    public String getShowTD(Model model, Authentication authentication, @PathVariable Long id) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("td", declarationTDService.getById(id).build());
        model.addAttribute("listProduct", productService.getAllProductByDeclaration(id));
        model.addAttribute("role", accountService.getRole(authentication.getName()));
        return "/client/showTD";
    }
    @GetMapping("/showListCMR")
    public String getShowListCMR(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("crmList", crmService.getAllCRM(authentication.getName()));
        return "/client/showListCMR";
    }
    @GetMapping("/showCMR/{id}")
    public String getShowCMR(Model model, Authentication authentication, @PathVariable Long id) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("crm", crmService.getCRMByID(id).build());
        return "/client/showCMR";
    }
    @GetMapping("/regProdPoDeclare")
    public String getRegProdPoDeclare(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("declarationList", declarationTDService.getAllDeclaration());
        return "/client/regProdPoDeclare";
    }
    @GetMapping("/markQuality")
    public String getMarkQuality(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("marks", markingInfoService.getAllMarking(authentication.getName()));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        return "/client/markQuality";
    }
    @GetMapping("/prinProdQuality")
    public String getPrinProdQuality(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("deliveryList", deliveryProductService.getAllShipmentByClient(authentication.getName()));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        return "/client/prinProdQuality";
    }
    @GetMapping("/otpProdQuality")
    public String getOtpProdQuality(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("otpuskList", otpuskService.getAllByAccountClient(authentication.getName()));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        return "/client/otpProdQuality";
    }
    @GetMapping("/comunicationQuality")
    public String getComunicationQuality(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("chatRoomList", chatRoomService.getAllByAccount(authentication.getName()));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        model.addAttribute("login", authentication.getName());
        return "/client/comunicationQuality";
    }
    @GetMapping("/chekOplata/{id}")
    private ResponseEntity<?> getImageByIDUser(@PathVariable Long id)
    {
        Otpusk qr = otpuskService.getByApplicationForRelease(id);
        Base64.Decoder decoder = Base64.getDecoder();
        return ResponseEntity.ok()
                .header("fileName", "src")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new ByteArrayInputStream(decoder.decode(qr.getSrc()))));
    }

}
