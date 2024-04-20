package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.*;
import com.example.diplomproject.model.dto.marking.ApplicationForMarkingDTO;
import com.example.diplomproject.model.entity.ApplicationForRelease;
import com.example.diplomproject.model.entity.Brand;
import com.example.diplomproject.model.entity.GoodTransportDocument;
import com.example.diplomproject.model.entity.StatusApplicationForRelease;
import com.example.diplomproject.model.entity.marking.TypeMarking;
import com.example.diplomproject.service.*;
import lombok.RequiredArgsConstructor;
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
    private final ApplicationForMarkingService applicationForMarkingService;
    private final ProductService productService;
    private final ApplicationForReleaseService applicationForRelease;

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
        model.addAttribute("truck", truckService.getTruck(authentication.getName()));
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
        model.addAttribute("newSupplier", new IndividualsDTO());
        return "/client/regAsAComp";
    }

    @GetMapping("/client/regAuto")
    public String getRegAuto(Model model, Authentication authentication) {
        model.addAttribute("brand", Brand.getRussianName());
        model.addAttribute("truck", new TruckDTO());
        model.addAttribute("trucks", truckService.getTruck(authentication.getName()));
        return "/client/regAuto";
    }

    @GetMapping("/client/showMarkedProduct")
    public String getShowMarkedProduct() {
        return "/client/showMarkedProduct";
    }

    @GetMapping("/client/addZavForMark")
    public String getAddZavForMark(Model model, Authentication authentication) {
        model.addAttribute("applicationsForMarking" , applicationForMarkingService.getAllApplicationsForMarking(authentication.getName()));
        model.addAttribute("applicationForMarking", new ApplicationForMarkingDTO());
        model.addAttribute("productList", productService.getAllProduct(authentication.getName()));
        model.addAttribute("typeMarkings", TypeMarking.getRussianName());
        return "/client/addZavForMark";
    }

    @GetMapping("/client/makeZavForOtp")
    public String getMakeZavForOtp(Model model, Authentication authentication) {

        model.addAttribute("productList", productService.getAllProduct(authentication.getName()));
        model.addAttribute("applicationForRelease", applicationForRelease.getAllApplicationForRelease(authentication.getName()));
        model.addAttribute("newApplicationForRelease", new ApplicationForReleaseDTO());
        model.addAttribute("StatusEnum", StatusApplicationForRelease.class);
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
