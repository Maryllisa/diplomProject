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
}
