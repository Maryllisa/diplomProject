package com.example.diplomproject.controller.manager.button;

import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/user/changeStatusApplicationForStorage")
public class ButtonStatusApplicationForStorageController {

    private final ApplicationService applicationService;

    @GetMapping("/PROCESSING/{id}")
    public String changePROCESSING(@PathVariable String id, Authentication authentication){
        applicationService.changeStatus(StatusApplication.PROCESSING, id, authentication.getName());
        return "redirect:/user/obrApp";
    }
    @GetMapping("/CANCELLED/{id}")
    public String changeCANCELLED(@PathVariable String id, Authentication authentication){
        applicationService.changeStatus(StatusApplication.CANCELLED, id, authentication.getName());
        return "redirect:/user/obrApp";
    }
    @GetMapping("/COMPLETED/{id}")
    public String changeCOMPLETED(@PathVariable String id, Authentication authentication){
        applicationService.changeStatus(StatusApplication.COMPLETED, id, authentication.getName());
        return "redirect:/user/activeApp";
    }


}
