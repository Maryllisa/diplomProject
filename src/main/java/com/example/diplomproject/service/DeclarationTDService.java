package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.DeclarationDTO;
import com.example.diplomproject.model.entity.Account;
import com.example.diplomproject.model.entity.Supplier;
import com.example.diplomproject.model.entity.declaration.CurrencyRate;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.declaration.FinancialRegulator;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Service
@AllArgsConstructor
public class DeclarationTDService {
    private final DeclarationTDRepository declarationTDRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final ProductService productService;
    private final AddressRepository addressRepository;
    private final FinancialRegulatorRepository financialRegulatorRepository;
    private final CurrencyRateRepository currencyRateRepository;

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
        DeclarationTD declarationTDForDB = declarationDTO.buildWithoutEntity();
        Account account = userRepository.findByLogin(login);
        Supplier supplierFromDB = new Supplier();
        if (supplierRepository.findByAccount(account).orElse(null) != null){
            supplierFromDB = supplierRepository.findByAccount(account).orElse(null);
        }
        else {
            supplierFromDB = supplierRepository.save(declarationDTO.getSenderDTO().build());
        }
        declarationTDForDB.setSupplier(supplierFromDB);
        declarationTDForDB.setRecipientAddress(addressRepository.save(declarationDTO.getRecipientDTO().build()));
        declarationTDForDB.setFinancialRegulator(financialRegulatorRepository.save(declarationDTO.getOtvetstvenoeFace().build()));
        declarationTDForDB.setDeclarant(addressRepository.save(declarationDTO.getDeclarator().build()));
        if (!declarationDTO.isFreeDeliveryCheckbox()){
            declarationTDForDB.setCurrency(declarationDTO.getCurrencyCode());
            declarationTDForDB.setAccountTotalAmount(declarationDTO.getInvoiceAmount());
        }
        else {
            declarationTDForDB.setCurrency("");
            declarationTDForDB.setAccountTotalAmount("");
        }
        declarationTDForDB.setCurrencyRate(currencyRateRepository.save(declarationDTO.getCurrencyRateDTO().build()));
        declarationTDForDB = declarationTDRepository.save(declarationTDForDB);
        declarationTDForDB.setProductList(productService.addNewProduct(declarationTDForDB, declarationDTO.getProductDTOS()));
    }
}
