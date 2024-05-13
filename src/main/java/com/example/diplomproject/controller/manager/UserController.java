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
    public String getStart() {
        return "/user/userPanel";
    }
    @GetMapping("/obrApp")
    public String getObrApplication(Model model, Authentication authentication) {

        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.PENDING));
        return "/user/obrApplication";
    }
    @GetMapping("/activeApp")
    public String getActiveApplication(Model model) {

        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.PROCESSING));
        return "/user/activeApplications";
    }
    @GetMapping("/appHistory")
    public String getApplicationHistory(Model model) {

        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.COMPLETED));
        return "/user/applicationHistory";
    }
    @GetMapping("/showSuppliers")
    public String getSupplierReg(Model model) {
        model.addAttribute("suppliers", individualsService.getAllSuppliers());
        return "/user/showSupplier";
    }
    @GetMapping("/regMark")
    public String getRegistrationMark(Model model) {
        model.addAttribute("applicationList", applicationForMarking.getAllApplicationsForMarking());
        model.addAttribute("newMarking", new MarkingInfoDTO());
        model.addAttribute("typeMarking", TypeMarking.getRussianName());
        return "/user/regMark";
    }
    @GetMapping("/showAllDeclaration")
    public String getAllDeclaration(Model model, Authentication authentication) {
        model.addAttribute("declaration", declarationTDService.getAllDeclarationByAccount(authentication.getName()));
        return "/user/showAllDeclaration";
    }
    @GetMapping("/showOfDeclaration")
    public String getRegOfDeclaration() {return "/user/showDeclaration";}
    @GetMapping("/showShipment")
    public String getShipmentList(Model model, Authentication authentication) {
        model.addAttribute("shipmentList", deliveryProductService.getAllShipment(authentication.getName()));
        return "/user/showShipment";
    }
    @GetMapping("/arrangeShipment")
    public String getArrangeShipment(Model model, Authentication authentication) {
        model.addAttribute("applicationForStorage", applicationForStorageService.getAllApplictionByAccountManagerAndStatus(authentication.getName()));
        return "/user/arrangeShipment";
    }
    @GetMapping("/showProductOnWH")
    public String getShowProductOnWH(Model model, Authentication authentication) {
        model.addAttribute("delivery", deliveryProductService.getAllShipment(authentication.getName()));
        return "/user/showProductOnWH";
    }
    @GetMapping("/relesionReg")
    public String getRelesionReg(Model model, Authentication authentication) {
        model.addAttribute("applicationList", applicationForReleaseService.getAllApplicationForReleaseAndStatus(StatusApplicationForRelease.IN_PROCESSING));
        return "/user/relesionReg";
    }
    @GetMapping("/showAllMarkedProd")
    public String getShowAllMarkedProd(Model model) {
        model.addAttribute("markingInfoList", markingInfoService.getAllMarking());
        return "/user/showAllMarkedProd";
    }
    @GetMapping("/showTD/{id}")
    public String getShowTDUser(Model model, Authentication authentication, @PathVariable Long id) {
        model.addAttribute("td", declarationTDService.getById(id).build());
        model.addAttribute("listProduct", productService.getAllProductByDeclaration(id));
        model.addAttribute("role", accountService.getRole(authentication.getName()));
        return "/client/showTD";
    }

}
