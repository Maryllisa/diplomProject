package com.example.diplomproject.controller.manager;
import com.example.diplomproject.model.entity.StatusApplication;
import com.example.diplomproject.model.entity.Supplier;
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
    private final SupplierService supplierService;
    @GetMapping("/user")
    public String getStart() {
        return "/user/userPanel";
    }
    @GetMapping("/user/obrApp")
    public String getObrApplication(Model model) {

        model.addAttribute("applications", applicationService.getAllApplication());
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
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "/user/showSupplier";
    }
    @GetMapping("/user/regMark")
    public String getRegistrationMark() {return "/user/regMark";}
    @GetMapping("/user/showAllDeclaration")
    public String getAllDeclaration() {return "/user/showAllDeclaration";}
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

}
