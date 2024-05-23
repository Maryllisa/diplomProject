package com.example.diplomproject.controller.admin;

import com.example.diplomproject.model.dto.MarkForAgencyDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.MarkForAgency;
import com.example.diplomproject.service.AccountService;
import com.example.diplomproject.service.CustomsAgencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class AdminResController {
    private final CustomsAgencyService customsAgencyService;
    private final AccountService accountService;

    @GetMapping("/admin/customsAgency/{id}")
    public List<MarkForAgency> getListMarkForAgency(@PathVariable Long id){
        return customsAgencyService.getById(id);
    }
    @PostMapping("/admin/regMark")
    public RedirectView addNewMarkForAgencyWight(@RequestParam("id") Long id,
                                                 @RequestParam("vesochMark") double vesochMark,
                                                 @RequestParam("vesochOtgr") double vesochOtgr,
                                                 @RequestParam("vesochOtp") double vesochOtp,
                                                 @RequestParam("vesochSviaz") double vesochSviaz,
                                                 @RequestParam("vesochSost") double vesochSost,
                                                 RedirectView redirectView){
        customsAgencyService.registrationMark(id, vesochMark,vesochOtgr, vesochOtp, vesochSviaz, vesochSost);
        redirectView.setUrl("/admin/formKachQuality");
        return redirectView;
    }
    @GetMapping("/admin/getListMarkForAgency")
    private MarkForAgencyDTO getAllMarkGorGRAF(){
        return customsAgencyService.getAllMarksForGraf();
    }
    @GetMapping("/admin/getCustomAgency")
    private List<Double> getAllCustomAgencyorGRAF(){
        return customsAgencyService.getAllCustomsAgency();
    }
    @GetMapping("/admin/findUser/{id}")
    public Account getUserById(@PathVariable Long id){
        return accountService.findUserById(id);
    }
}
