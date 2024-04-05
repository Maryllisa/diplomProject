package com.example.diplomproject;

import com.example.diplomproject.model.dto.AccountDTO;
import com.example.diplomproject.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@RestController
public class MainControllerRest {
    private final AccountService accountService;
    @SneakyThrows
    @PostMapping("/registration")
    public ResponseEntity<String> addNewUser(@Valid @ModelAttribute AccountDTO account,
                                             BindingResult result,
                                             @RequestParam String role,
                                             @RequestParam("file") MultipartFile file,
                                             Model model) {
        if (result.hasErrors()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(accountService.checkNewAccount(result, account));
            return ResponseEntity.badRequest().body(body);
        }
        else if(!accountService.checkLoginAndEmail(account).isEmpty()) {
            Map<String, String> map = accountService.checkLoginAndEmail(account);
            ObjectMapper objectMapper = new ObjectMapper();
            return ResponseEntity.badRequest().body(objectMapper.writeValueAsString(map));
        }
        else {
            accountService.addNewAccount(account, role, file);
        }
        return ResponseEntity.ok("User created successfully");
    }
}
