package com.example.diplomproject.controller.manager;
import com.example.diplomproject.model.dto.MarkingInfoDTO;
import com.example.diplomproject.model.entity.MarkingInfo;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserController {
    private final ApplicationService applicationService;
    private final IndividualsService individualsService;
    private final DeclarationTDService declarationTDService;
    private final ApplicationForMarkingService applicationForMarking;

    @GetMapping("/user")
    public String getStart() {
        return "/user/userPanel";
    }
    @GetMapping("/user/obrApp")
    public String getObrApplication(Model model, Authentication authentication) {

        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.PENDING));
        return "/user/obrApplication";
    }
    @GetMapping("/user/activeApp")
    public String getActiveApplication(Model model) {

        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.PROCESSING));
        return "/user/activeApplications";
    }
    @GetMapping("/user/appHistory")
    public String getApplicationHistory(Model model) {

        model.addAttribute("applications", applicationService.getApplicationTrue(StatusApplication.COMPLETED));
        return "/user/applicationHistory";
    }
    @GetMapping("/user/showSuppliers")
    public String getSupplierReg(Model model) {
        model.addAttribute("suppliers", individualsService.getAllSuppliers());
        return "/user/showSupplier";
    }
    @GetMapping("/user/regMark")
    public String getRegistrationMark(Model model) {
        model.addAttribute("applicationList", applicationForMarking.getAllApplicationsForMarking());
        model.addAttribute("newMarking", new MarkingInfoDTO());
        model.addAttribute("typeMarking", TypeMarking.getRussianName());
        return "/user/regMark";
    }
    @GetMapping("/user/showAllDeclaration")
    public String getAllDeclaration(Model model) {
        model.addAttribute("declaration", declarationTDService.getAllDeclaration());
        return "/user/showAllDeclaration";
    }
    @GetMapping("/user/showOfDeclaration")
    public String getRegOfDeclaration() {return "/user/showDeclaration";}
    @GetMapping("/user/showShipment")
    public String getShipmentList() {return "/user/showShipment";}
    @GetMapping("/user/arrangeShipment")
    public String getArrangeShipment() {return "/user/arrangeShipment";}
    @GetMapping("/user/showProductOnWH")
    public String getShowProductOnWH() {return "/user/showProductOnWH";}
    @GetMapping("/user/relesionReg")
    public String getRelesionReg() {return "/user/relesionReg";}
    @GetMapping("/user/showAllMarkedProd")
    public String getShowAllMarkedProd() {return "/user/showAllMarkedProd";}

}
