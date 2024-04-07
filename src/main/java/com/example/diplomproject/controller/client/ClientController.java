package com.example.diplomproject.controller.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {

    @GetMapping("/client")
    public String getClient() {return "/client/clientPanel";}
    @GetMapping("/client/supplierReg")
    public String getSupplierReg() {return "/client/supplierReg";}
    @GetMapping("/client/regOfDeclaration")
    public String getRegOfDeclaration() {return "/client/regOfDeclaration";}
    @GetMapping("/client/addStorageRequest")
    public String getAddStorageRequest() {return "/client/addStorageRequest";}
    @GetMapping("/client/addTTN")
    public String getAddTTN() {return "/client/addTTN";}
    @GetMapping("/client/zavStatus")
    public String getZavStatus() {return "/client/zavStatus";}
    @GetMapping("/client/prodStatus")
    public String getProdStatus() {return "/client/prodStatus";}
}
