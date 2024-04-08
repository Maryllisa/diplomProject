package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.service.DeclarationTDService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class ClientRestController {
    private final DeclarationTDService declarationTDService;

    @SneakyThrows
    @PostMapping("/client/regOfDeclaration")
    private ResponseEntity<String> checkAddNewDeclaration(@Valid @ModelAttribute DeclarationDTO declarationDTO,
                                                          BindingResult result,
                                                          @RequestParam String role, Model model, Authentication authentication){
        if (result.hasErrors()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(declarationTDService.checkNewDeclaration(result, declarationDTO));
            return ResponseEntity.badRequest().body(body);
        }
        declarationTDService.addNewDeclaration(declarationDTO, authentication.getName());

        return ResponseEntity.ok("Есть контакт!!");

    }

}
