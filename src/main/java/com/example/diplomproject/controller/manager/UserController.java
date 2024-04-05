package com.example.diplomproject.controller.manager;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UserController {
    @GetMapping("/user")
    public String getStart() {
        return "/user/userPanel";
    }
    @GetMapping("/user/obrApp")
    public String getObrApplication() {
        return "/user/obrApplication";
    }
    @GetMapping("/user/activeApp")
    public String getActiveApplication() {
        return "/user/activeApplications";
    }
    @GetMapping("/user/appHistory")
    public String getApplicationHistory() {return "/user/applicationHistory";}
    @GetMapping("/user/supplierReg")
    public String getSupplierReg() {return "/user/supplierReg";}
    @GetMapping("/user/regMark")
    public String getRegistrationMark() {return "/user/regMark";}
    @GetMapping("/user/regOfDeclaration")
    public String getRegOfDeclaration() {return "/user/regOfDeclaration";}
    @GetMapping("/user/showShipment")
    public String getShipmentList() {return "/user/showShipment";}
    @GetMapping("/user/arrangeShipment")
    public String getArrangeShipment() {return "/user/arrangeShipment";}
    @GetMapping("/user/showProductOnWH")
    public String getShowProductOnWH() {return "/user/showProductOnWH";}
}
