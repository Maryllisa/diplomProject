package com.example.diplomproject;

import com.example.diplomproject.model.dto.AccountDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        model.addAttribute("login", login);
        return "chat/chatApp";
    }
    @GetMapping("/login")
    private String getLogin(){
        return "main/login";
    }

}
