package com.example.diplomproject.controller.admin;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.service.DeclarationTDService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final DeclarationTDService declarationTDService;

    @GetMapping("/admin")
    public String getAdmin() {return "/admin/adminPanel";}

    @GetMapping("/admin/showAccounts")
    public String getShowAccounts() {return "/admin/showAccounts";}
    @GetMapping("/admin/showAdmins")
    public String getShowAdmins() {return "/admin/showAdmins";}
}
