package com.example.diplomproject.controller.admin;

import com.example.diplomproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final CustomsAgencyService customsAgencyService;
    private final ApplicationForStorageService applicationForStorageService;
    private final AccountService accountService;
    private final DeliveryProductService deliveryProductService;
    private final OtpuskService otpuskService;
    private final MarkingInfoService markingInfoService;

    @GetMapping("/admin")
    public String getAdmin(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/admin/adminPanel";
    }

    @GetMapping("/admin/showAccounts")
    public String getShowAccounts(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("userList", accountService.getAllUserWithoutAdmin());
        return "/admin/showAccounts";
    }
    @GetMapping("/admin/formKachQuality")
    public String getFormKachQuality(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("customsAgencyList", customsAgencyService.getAllNotMark());
        return "/admin/formKachQuality";
    }
    @GetMapping("/admin/showOcForObsl")
    public String getShowOcForObsl(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("customsAgencyList", customsAgencyService.getAllTrueMark());
        return "/admin/showOcForObsl";
    }
    @GetMapping("/admin/otchOfProvisionServ")
    public String getOtchOfProvisionServ(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        return "/admin/otchOfProvisionServ";}
    @GetMapping("/admin/showAppJournal")
    public String getShowAppJournal(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("application", applicationForStorageService.getAll());
        return "/admin/showAppJournal";
    }
    @GetMapping("/admin/showOtgrJournal")
    public String getShowOtgrJournal(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("shipmentList", deliveryProductService.getAll());
        return "/admin/showOtgrJournal";
    }
    @GetMapping("/admin/showOtpJournal")
    public String getShowOtpJournal(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("otpusk", otpuskService.getAll());
        return "/admin/showOtpJournal";
    }
    @GetMapping("/admin/showMarkedProd")
    public String getShowMarkedProd(Model model, Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("markingInfoList", markingInfoService.getAllMarking());
        return "/admin/showMarkedProd";
    }
    @GetMapping("/admin/ShowProdOnWH")
    public String getShowProdOnWH(Model model
            , Authentication authentication) {
        model.addAttribute("person", accountService.getAccount(authentication.getName()));
        model.addAttribute("delivery", deliveryProductService.getAll());
        return "/admin/ShowProdOnWH";
    }

}

