package com.example.diplomproject;

import com.example.diplomproject.model.dto.AccountDTO;
import com.example.diplomproject.model.entity.enumStatus.Role;
import com.example.diplomproject.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class MainController {
    private final AccountService accountService;
    @GetMapping("/")
    public String mainStart(Model model) {
        return "main/startPage";
    }
    @GetMapping("/registration")
    public String getRegistration(Model model){
        model.addAttribute("account", new AccountDTO());
        return "main/registration";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = accountService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "Профиль успешно активирован!");
        } else {
            model.addAttribute("message", "Ошибка активации...");
        }
        return "main/login";
    }
    @GetMapping("/openChat")
    public String getChat(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        accountService.changeStatusOnline(login);
        model.addAttribute("login", login);
        return "chat/chatApp";
    }
    @GetMapping("/openChat/logout")
    public String logoutFromChat(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        accountService.changeStatusOffline(login);
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals(Role.ADMIN.toString())){
            return "redirect:/admin";
        }
        else if (role.equals(Role.CLIENT.toString())){
            return "redirect:/client";
        }
        else {
            return "redirect:/user";
        }
    }
    @GetMapping("/login")
    private String getLogin(){
        return "main/login";
    }

}
