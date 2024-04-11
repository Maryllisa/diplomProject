package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.service.DeclarationTDService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final DeclarationTDService declarationTDService;

    @GetMapping("/client")
    public String getClient() {return "/client/clientPanel";}
    @GetMapping("/client/supplierReg")
    public String getSupplierReg() {return "/client/supplierReg";}
    @GetMapping("/client/regOfDeclaration")
    public String getRegOfDeclaration(Model model, Authentication authentication) {
        String login = authentication.getName();
        model.addAttribute("declaration", declarationTDService.geFormForNewDeclaration(login));
        return "/client/regOfDeclaration";
    }
    @GetMapping("/client/addStorageRequest")
    public String getAddStorageRequest() {return "/client/addStorageRequest";}
    @GetMapping("/client/addTTN")
    public String getAddTTN() {return "/client/addTTN";}
    @GetMapping("/client/zavStatus")
    public String getZavStatus() {return "/client/zavStatus";}
    @GetMapping("/client/prodStatus")
    public String getProdStatus() {return "/client/prodStatus";}
    @GetMapping("/client/addCMR")
    public String getAddCMR() {return "/client/addCMR";}
    @GetMapping("/client/regAsAComp")
    public String getRegAsAComp() {return "/client/regAsAComp";}
    @GetMapping("/client/regAuto")
    public String getRegAuto() {return "/client/regAuto";}
    @GetMapping("/client/showMarkedProduct")
    public String getShowMarkedProduct() {return "/client/showMarkedProduct";}
    @GetMapping("/client/addZavForMark")
    public String getAddZavForMark() {return "/client/addZavForMark";}
    @GetMapping("/client/makeZavForOtp")
    public String getMakeZavForOtp() {return "/client/makeZavForOtp";}
    @GetMapping("/client/showBillForSave")
    public String getShowBillForSave() {return "/client/showBillForSave";}
}
