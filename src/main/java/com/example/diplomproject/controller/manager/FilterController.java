package com.example.diplomproject.controller.manager;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/filter/")
public class FilterController {
    private final ApplicationService applicationService;
    private final IndividualsService individualsService;
    private final DeclarationTDService declarationTDService;
    private final ApplicationForMarkingService applicationForMarking;
    private final MarkingInfoService markingInfoService;
    private final ProductService productService;
    private final AccountService accountService;
    private final ApplicationForStorageService applicationForStorageService;
    private final DeliveryProductService deliveryProductService;
    private final ApplicationForReleaseService applicationForReleaseService;


    @GetMapping("/obrApp")
    public String getObrApplication(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {

        model.addAttribute("applications", applicationService
                .getApplicationTrue(StatusApplication.PENDING, searchData));
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/obrApplication";
    }

    @GetMapping("/activeApp")
    public String getActiveApplication(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applications", applicationService
                .getApplicationTrue(StatusApplication.PROCESSING, searchData));
        return "/user/activeApplications";
    }

    @GetMapping("/appHistory")
    public String getApplicationHistory(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applications", applicationService
                .getApplicationTrue(StatusApplication.COMPLETED, searchData));
        return "/user/applicationHistory";
    }

    @GetMapping("/showSuppliers")
    public String getSupplierReg(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("suppliers", individualsService.getAllSuppliers(searchData));
        return "/user/showSupplier";
    }

    @GetMapping("/regMark")
    public String getRegistrationMark(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationList", applicationForMarking.getAllApplicationsForMarking(searchData));
        model.addAttribute("newMarking", new MarkingInfoDTO());
        model.addAttribute("typeMarking", TypeMarking.getRussianName());
        return "/user/regMark";
    }

    @GetMapping("/showAllDeclaration")
    public String getAllDeclaration(@ModelAttribute SearchData searchData, Model model,
                                    Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("declaration", declarationTDService.getAllDeclarationByAccount(authentication.getName(), searchData));
        return "/user/showAllDeclaration";
    }
    @GetMapping("/showShipment")
    public String getShipmentList(@ModelAttribute SearchData searchData, Model model,
                                  Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("shipmentList", deliveryProductService.getAllShipment(authentication.getName(), searchData));
        return "/user/showShipment";
    }

    @GetMapping("/relesionReg")
    public String getRelesionReg(@ModelAttribute SearchData searchData, Model model
            , Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationList",
                applicationForReleaseService.getAllApplicationForReleaseAndStatus(authentication.getName(), StatusApplicationForRelease.IN_PROCESSING, searchData));
        return "/user/relesionReg";
    }
    @GetMapping("/showAllMarkedProd")
    public String getShowAllMarkedProd(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("markingInfoList", markingInfoService.getAllMarking(searchData));
        return "/user/showAllMarkedProd";
    }

    @GetMapping("/showProductOnWH")
    public String getShowProductOnWH(@ModelAttribute SearchData searchData, Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("delivery", deliveryProductService.getAllShipment(authentication.getName(), searchData));
        return "/user/showProductOnWH";
    }
}
