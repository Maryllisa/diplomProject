package com.example.diplomproject.controller.manager;

import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.ApplicationForRelease;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.enumStatus.StatusApplicationForRelease;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
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

    @GetMapping("")
    public String getStart(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/userPanel";
    }

    @GetMapping("/obrApp")
    public String getObrApplication(Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.PENDING));
        return "/user/obrApplication";
    }

    @GetMapping("/activeApp")
    public String getActiveApplication(Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.PROCESSING));
        return "/user/activeApplications";
    }

    @GetMapping("/appHistory")
    public String getApplicationHistory(Model model, Authentication authentication) {

        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.COMPLETED));
        return "/user/applicationHistory";
    }

    @GetMapping("/showSuppliers")
    public String getSupplierReg(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("suppliers", individualsService.getAllSuppliers());
        return "/user/showSupplier";
    }

    @GetMapping("/regMark")
    public String getRegistrationMark(Model model, Authentication authentication) {
        model.addAttribute("applicationList", applicationForMarking.getAllApplicationsForMarking());
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("newMarking", new MarkingInfoDTO());
        model.addAttribute("typeMarking", TypeMarking.getRussianName());
        return "/user/regMark";
    }

    @GetMapping("/showAllDeclaration")
    public String getAllDeclaration(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("declaration", declarationTDService.getAllDeclarationByAccount(authentication.getName()));
        return "/user/showAllDeclaration";
    }

    @GetMapping("/showOfDeclaration")
    public String getRegOfDeclaration(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/showDeclaration";
    }

    @GetMapping("/showShipment")
    public String getShipmentList(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("shipmentList", deliveryProductService.getAllShipment(authentication.getName()));
        return "/user/showShipment";
    }

    @GetMapping("/arrangeShipment")
    public String getArrangeShipment(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationForStorage", applicationForStorageService.getAllApplictionByAccountManagerAndStatus(authentication.getName()));
        return "/user/arrangeShipment";
    }

    @GetMapping("/showProductOnWH")
    public String getShowProductOnWH(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("delivery", deliveryProductService.getAllShipment(authentication.getName()));
        return "/user/showProductOnWH";
    }

    @GetMapping("/relesionReg")
    public String getRelesionReg(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("applicationList", applicationForReleaseService.getAllApplicationForReleaseAndStatus(authentication.getName(), StatusApplicationForRelease.IN_PROCESSING));
        return "/user/relesionReg";
    }

    @GetMapping("/showAllMarkedProd")
    public String getShowAllMarkedProd(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("markingInfoList", markingInfoService.getAllMarking());
        return "/user/showAllMarkedProd";
    }

    @GetMapping("/showTD/{id}")
    public String getShowTDUser(Model model, Authentication authentication, @PathVariable Long id) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("td", declarationTDService.getById(id).build());
        model.addAttribute("listProduct", productService.getAllProductByDeclaration(id));
        model.addAttribute("role", accountService.getRole(authentication.getName()));
        return "/client/showTD";
    }

    @GetMapping("/otchPoMarkProd")
    public String getOtchPoMarkProd(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/otchPoMarkProd";
    }

    @GetMapping("/otchAbProdOnWH")
    public String getOtchAbProdOnWH(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/otchAbProdOnWH";
    }

    @GetMapping("/otchPoActApp")
    public String getOtchPoActApp(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/otchPoActApp";
    }

    @GetMapping("/otchAppHistory")
    public String getOtchAppHistory(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/user/otchAppHistory";
    }

    @GetMapping("/otchPoOtgr")
    public String getOtchPoOtgr(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));

        return "/user/otchPoOtgr";
    }

    @GetMapping("/otchPoOtpProd")
    public String getOtchPoOtpProd(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));

        return "/user/otchPoOtpProd";
    }

}
