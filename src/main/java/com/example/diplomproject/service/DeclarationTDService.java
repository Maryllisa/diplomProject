package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.dto.SupplierDTO;
import com.example.diplomproject.model.dto.dtoForDeclaration.AddressDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.Supplier;
import com.example.diplomproject.model.entity.declaration.Address;
import com.example.diplomproject.model.entity.declaration.CurrencyRate;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.declaration.ProductLocation;
import com.example.diplomproject.repository.DeclarationTDRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.repository.SupplierRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DeclarationTDService {
    private final DeclarationTDRepository declarationTDRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public DeclarationDTO geFormForNewDeclaration(String login) {
        DeclarationDTO declarationDTO = new DeclarationDTO();
        Account account = userRepository.findByLogin(login);
        Supplier supplier = supplierRepository.findByAccount(account).orElse(null);
        if(supplier!=null){
            declarationDTO.setSenderDTO(supplier.buildDTO());
        }
        return declarationDTO;

    }
    public Map<String, String> checkNewDeclaration(BindingResult result, DeclarationDTO declarationDTO) {
        return null;
    }
    public void addNewDeclaration(DeclarationDTO declarationDTO, String login){
        DeclarationTD declarationTD = new DeclarationTD();
        declarationTD.setDeclarationNumber(declarationDTO.getCustomEDCode() +"/"+declarationDTO.getDirectionOfMovement()+"/" + declarationDTO.getProcedureCode());
        Account account = userRepository.findByLogin(login);
        Supplier supplier = supplierRepository.findByAccount(account).orElse(null);
        boolean check = false;
        if(supplier==null) {
            supplierRepository.save(declarationDTO.getSenderDTO().build());
            check = true;
        }
        declarationTD = declarationDTO.build();

        declarationTD = declarationTDRepository.save(declarationTD);
        List<Product>  productList = new ArrayList<>();
        for(ProductDTO pr: declarationDTO.getProductDTOS()){
            Product product = pr.build();
            product.setDeclarationTD(declarationTD);
            productList.add(productRepository.save(product));
        }
        declarationTD.setProductList(productList);
        declarationTDRepository.save(declarationTD);
    }
}
