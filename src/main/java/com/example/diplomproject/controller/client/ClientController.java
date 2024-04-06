package com.example.diplomproject.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {

    @GetMapping("/user/supplierReg")
    public String getSupplierReg() {return "/client/supplierReg";}
}
