package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.CRM;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.model.entity.Individuals;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.service.CRMService;
import com.example.diplomproject.service.DeclarationTDService;
import com.example.diplomproject.service.GoodTransportDocumentService;
import com.example.diplomproject.service.IndividualsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final DeclarationTDService declarationTDService;
    private final IndividualsService individualsService;
    private final CRMService crmService;

    @GetMapping("/client")
    public String getClient() {
        return "/client/clientPanel";
    }

    @GetMapping("/client/supplierReg")
    public String getSupplierReg() {
        return "/client/supplierReg";
    }

    @GetMapping("/client/regOfDeclaration")
    public String getRegOfDeclaration(Model model, Authentication authentication) {
        String login = authentication.getName();
        model.addAttribute("declaration", declarationTDService.geFormForNewDeclaration(login));
        return "/client/regOfDeclaration";
    }

    @GetMapping("/client/addStorageRequest")
    public String getAddStorageRequest() {
        return "/client/addStorageRequest";
    }

    @GetMapping("/client/addTTN")
    public String getAddTTN(Model model, Authentication authentication) {
        model.addAttribute("gtd", new GoodTransportDocument());
        return "/client/addTTN";
    }

    @GetMapping("/client/zavStatus")
    public String getZavStatus() {
        return "/client/zavStatus";
    }

    @GetMapping("/client/prodStatus")
    public String getProdStatus() {
        return "/client/prodStatus";
    }

    @GetMapping("/client/addCMR")
    public String getAddCMR(Model model, Authentication authentication) {
        model.addAttribute("crm", crmService.getCRM(authentication.getName()));
        return "/client/addCMR";
    }

    @GetMapping("/client/regAsAComp")
    public String getRegAsAComp(Model model, Authentication authentication) {
        model.addAttribute("supplier", individualsService.getSuppliers(authentication.getName()));
        return "/client/regAsAComp";
    }

    @GetMapping("/client/regAuto")
    public String getRegAuto() {
        return "/client/regAuto";
    }

    @GetMapping("/client/showMarkedProduct")
    public String getShowMarkedProduct() {
        return "/client/showMarkedProduct";
    }

    @GetMapping("/client/addZavForMark")
    public String getAddZavForMark() {
        return "/client/addZavForMark";
    }

    @GetMapping("/client/makeZavForOtp")
    public String getMakeZavForOtp() {
        return "/client/makeZavForOtp";
    }

    @GetMapping("/client/showBillForSave")
    public String getShowBillForSave() {
        return "/client/showBillForSave";
    }

    @GetMapping("/client/priceForSave")
    public String getPriceForSave() {
        return "/client/priceForSave";
    }
}
