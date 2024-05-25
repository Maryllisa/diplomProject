package com.example.diplomproject.controller.admin;

import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/filter")
public class FilterControllerAdmin {
    private final CustomsAgencyService customsAgencyService;
    private final ApplicationForStorageService applicationForStorageService;
    private final AccountService accountService;
    private final DeliveryProductService deliveryProductService;
    private final OtpuskService otpuskService;
    private final MarkingInfoService markingInfoService;

    @GetMapping("/showAppJournal")
    public String getShowAppJournal(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("application", applicationForStorageService.getAll(searchData));
        return "/admin/showAppJournal";
    }
    @GetMapping("/showOtgrJournal")
    public String getShowOtgrJournal(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("shipmentList", deliveryProductService.getAll(searchData));
        return "/admin/showOtgrJournal";
    }
    @GetMapping("/showOtpJournal")
    public String getShowOtpJournal(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("otpusk", otpuskService.getAll(searchData));
        return "/admin/showOtpJournal";
    }
    @GetMapping("/showMarkedProd")
    public String getShowMarkedProd(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("markingInfoList", markingInfoService.getAllMarking(searchData));
        return "/admin/showMarkedProd";
    }
    @GetMapping("/ShowProdOnWH")
    public String getShowProdOnWH(Model model, @ModelAttribute SearchData searchData) {
        model.addAttribute("delivery", deliveryProductService.getAll(searchData));
        return "/admin/ShowProdOnWH";
    }
}
