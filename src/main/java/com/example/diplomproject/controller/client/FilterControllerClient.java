package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.ApplicationForReleaseDTO;
import com.example.diplomproject.model.dto.IndividualsDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.MarkForAgency;
import com.example.diplomproject.model.entity.Otpusk;
import com.example.diplomproject.model.entity.enumStatus.Brand;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/client/filter/")
public class FilterControllerClient {

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


    @GetMapping("/zavStatus")
    public String getZavStatus(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("applicationList", applicationForStorage.
                getAllApplicationByAccount(authentication.getName(), searchData));
        return "/client/zavStatus";
    }

    @GetMapping("/prodStatus")
    public String getProdStatus(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("productList", productService.getAllProduct(authentication.getName(), searchData));
        return "/client/prodStatus";
    }


    @GetMapping("/regAsAComp")
    public String getRegAsAComp(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("supplier", individualsService.getSuppliers(authentication.getName(), searchData));
        model.addAttribute("newSupplier", new IndividualsDTO());
        return "/client/regAsAComp";
    }

    @GetMapping("/regAuto")
    public String getRegAuto(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("brand", Brand.getRussianName());
        model.addAttribute("truck", new TruckDTO());
        model.addAttribute("trucks", truckService.getTruck(authentication.getName(), searchData));
        return "/client/regAuto";
    }

    @GetMapping("/showMarkedProduct")
    public String getShowMarkedProduct(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("marks", markingInfoService.getAllMarking(authentication.getName(), searchData));
        return "/client/showMarkedProduct";
    }

    @GetMapping("/addZavForMark")
    public String getAddZavForMark(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("applicationsForMarking" , applicationForMarkingService.getAllApplicationsForMarking(authentication.getName(), searchData));
        model.addAttribute("applicationForMarking", new ApplicationForMarkingDTO());
        model.addAttribute("productList", productService.getAllProduct(authentication.getName()));
        model.addAttribute("typeMarkings", TypeMarking.getRussianName());
        return "/client/addZavForMark";
    }

    @GetMapping("/makeZavForOtp")
    public String getMakeZavForOtp(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {

        model.addAttribute("productList", productService.getAllProductByApplication(authentication.getName()));
        model.addAttribute("applicationForRelease", applicationForRelease.getAllApplicationForRelease(authentication.getName(), searchData));
        model.addAttribute("newApplicationForRelease", new ApplicationForReleaseDTO());
        model.addAttribute("StatusEnum", StatusApplicationForRelease.class);
        return "/client/makeZavForOtp";
    }

    @GetMapping("/showBillForSave")
    public String getShowBillForSave(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("oplataList", otpuskService.getAllByLogin(authentication.getName(), searchData));
        return "/client/showBillForSave";
    }

    @GetMapping("/priceForSave")
    public String getPriceForSave(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("applicationList", applicationForRelease.getAllApplicationForReleaseAndStatus(StatusApplicationForRelease.AWAITING_PAYMENT, authentication.getName(), searchData));
        model.addAttribute("newOtpusk", new Otpusk());
        return "/client/priceForSave";
    }

    @GetMapping("/showListTTN")
    public String getShowListTTN(Authentication authentication, @ModelAttribute SearchData searchData, Model model) {
        model.addAttribute("ttn", goodTransportDocumentService.getAllByAccaount(authentication.getName()));
        return "/client/showListTTN";
    }
    @GetMapping("/showListTD")
    public String getShowListTD(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("tdList", declarationTDService.findAllByAccount(authentication.getName(), searchData));
        return "/client/showListTD";
    }
    @GetMapping("/showListCMR")
    public String getShowListCMR(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("crmList", crmService.getAllCRM(authentication.getName(), searchData));
        return "/client/showListCMR";
    }
    @GetMapping("/markQuality")
    public String getMarkQuality(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("marks", markingInfoService.getAllMarking(authentication.getName(), searchData));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        return "/client/markQuality";
    }
    @GetMapping("/prinProdQuality")
    public String getPrinProdQuality(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("deliveryList", deliveryProductService.getAllShipmentByClient(authentication.getName(), searchData));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        return "/client/prinProdQuality";
    }
    @GetMapping("/otpProdQuality")
    public String getOtpProdQuality(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("otpuskList", otpuskService.getAllByAccountClient(authentication.getName(), searchData));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        return "/client/otpProdQuality";
    }
    @GetMapping("/comunicationQuality")
    public String getComunicationQuality(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("chatRoomList", chatRoomService.getAllByAccount(authentication.getName(), searchData));
        model.addAttribute("newMarkForAgency", new MarkForAgency());
        model.addAttribute("login", authentication.getName());
        return "/client/comunicationQuality";
    }
}
