package com.example.diplomproject.controller.client;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.service.DeclarationTDService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class ClientRestController {
    private final DeclarationTDService declarationTDService;


    // Добавить валидацию
    @SneakyThrows
    @PostMapping("/client/regOfDeclaration")
    private ResponseEntity<String> checkAddNewDeclaration(@ModelAttribute DeclarationDTO declarationDTO,
                                                          BindingResult result,
                                                          Model model, Authentication authentication, HttpSession session){

//        if (result.hasErrors()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            String body = objectMapper.writeValueAsString(declarationTDService.checkNewDeclaration(result, declarationDTO));
//            return ResponseEntity.badRequest().body(body);
//        }
        List<ProductDTO> productDTOList = (List<ProductDTO>) session.getAttribute("productDTOList");
        declarationDTO.setProductDTOS(productDTOList);
        declarationTDService.addNewDeclaration(declarationDTO, authentication.getName());

        return ResponseEntity.ok("Есть контакт!!");

    }
    @PostMapping("/client/registrationProduct")
    private ResponseEntity<String> addNewProduct(@RequestBody List<ProductDTO> productDTOList, HttpSession session){
        session.setAttribute("productDTOList", productDTOList);
        return  ResponseEntity.ok("Товары добавленны");
    }

}
