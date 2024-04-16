package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.ApplicationForStorageDTO;
import com.example.diplomproject.model.dto.CRMDTO;
import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.dto.TruckDTO;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.Brand;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final DeclarationTDService declarationTDService;
    private final IndividualsService individualsService;
    private final CRMService crmService;
    private final GoodTransportDocumentService goodTransportDocumentService;
    private final TruckService truckService;

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
        model.addAttribute("supplier", declarationTDService.getSupplier(login));
        model.addAttribute("declaration", new DeclarationDTO());
        return "/client/regOfDeclaration";
    }

    @GetMapping("/client/addStorageRequest")
    public String getAddStorageRequest(Model model, Authentication authentication) {

        model.addAttribute("gpd", goodTransportDocumentService.getAllByAccaount(authentication.getName()));
        model.addAttribute("declaration", declarationTDService.findAllByAccount(authentication.getName()));
        model.addAttribute("crm", crmService.findAllByAccount(authentication.getName()));
        model.addAttribute("truck", truckService.findAll());
        model.addAttribute("application", new ApplicationForStorageDTO());
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
        model.addAttribute("supplier", declarationTDService.getSupplier(authentication.getName()));
        model.addAttribute("crm", new CRMDTO());
        return "/client/addCMR";
    }

    @GetMapping("/client/regAsAComp")
    public String getRegAsAComp(Model model, Authentication authentication) {
        model.addAttribute("supplier", individualsService.getSuppliers(authentication.getName()));
        return "/client/regAsAComp";
    }

    @GetMapping("/client/regAuto")
    public String getRegAuto(Model model) {
        model.addAttribute("brand", Brand.getRussianName());
        model.addAttribute("truck", new TruckDTO());
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
