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
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setOrganizationName(supplier.getOrganizationName());
            supplierDTO.setAddress(new AddressDTO(supplier.getAddress().getCity(),
                    supplier.getAddress().getPostalCode(),
                    supplier.getAddress().getRegion(),
                    supplier.getAddress().getSettlement(),
                    supplier.getAddress().getBuild(),
                    supplier.getAddress().getOgrnNumber()));
            supplierDTO.setPhone(supplier.getPhone());
            supplierDTO.setBankCode(supplier.getBankCode());
            supplierDTO.setLegalAddress(supplierDTO.getLegalAddress());
            supplierDTO.setRegistrationCode(supplier.getRegistrationCode());
            supplierDTO.setTaxId(supplier.getTaxId());
            supplierDTO.setBankName(supplier.getBankName());
            declarationDTO.setSenderDTO(supplierDTO);
        }
        return declarationDTO;

    }
    public Supplier convertToSuppliers(SupplierDTO dto){
        Supplier supplier = new Supplier();
        supplier.setOrganizationName(dto.getOrganizationName());
        supplier.setAddress(new Address(dto.getAddress().getCity(),
                dto.getAddress().getPostalCode(),
                dto.getAddress().getRegion(),
                dto.getAddress().getSettlement(),
                dto.getAddress().getBuild(),
                dto.getAddress().getOgrnNumber()));
        supplier.setPhone(dto.getPhone());
        supplier.setBankCode(dto.getBankCode());
        supplier.setLegalAddress(dto.getLegalAddress());
        supplier.setRegistrationCode(dto.getRegistrationCode());
        supplier.setTaxId(dto.getTaxId());
        supplier.setBankName(dto.getBankName());
        return supplier;
    }
    public Map<String, String> checkNewDeclaration(BindingResult result, DeclarationDTO declarationDTO) {
        return null;
    }
    public void addNewDeclaration(DeclarationDTO declarationDTO, String login){
        DeclarationTD declarationTD = new DeclarationTD();
        declarationTD.setDeclarationNumber(declarationDTO.getCustomEDCode() +"/"+declarationDTO.getDirectionOfMovement()+"/" + declarationDTO.getProcedureCode());
        Account account = userRepository.findByLogin(login);
        Supplier supplier = supplierRepository.findByAccount(account).orElse(null);
        if(supplier==null) {
            supplierRepository.save(convertToSuppliers(declarationDTO.getSenderDTO()));
        }
        else declarationTD.setSupplier(supplier);
        declarationTD.setFormGr3(declarationDTO.getNumbList() + " "+ declarationDTO.getColList());
        declarationTD.setSpecification(declarationDTO.getColSpec()+"/"+declarationDTO.getColListSpec());
        declarationTD.setColProducts(declarationDTO.getColProd());
        declarationTD.setAllPlace(declarationDTO.getColMest());
        declarationTD.setDeclarationDetails(declarationDTO.getOsobenOfDeclar());
        Supplier recipient = convertToSuppliers(declarationDTO.getRecipientDTO());
        declarationTD.setRecipientCompany(recipient.getOrganizationName());
        declarationTD.setRecipientAddress(recipient.getAddress());
        // тут про ответсвенное лицо, но я не придумала зачем мне эта инфа, поэтому куда ее засунуть соответственно
        declarationTD.setTorgCountry(declarationDTO.getTradingCountry());
        declarationTD.setCost(declarationDTO.getTamPrice());
        Supplier declarator = convertToSuppliers(declarationDTO.getDeclarator());
        declarationTD.setDeclarant(declarator.getAddress());
        declarationTD.setCodeSenderCountry(declarationDTO.getCountryCodeDeparture());
        declarationTD.setNameSenderCountry(declarationDTO.getCountryNameDeparture());
        declarationTD.setCodeOriginCountry(declarationDTO.getCountryCodeOrigin());
        declarationTD.setNameOriginCountry(declarationDTO.getCountryNameOrigin());
        declarationTD.setCodeRecipientCountry(declarationDTO.getCountryCodeDestination());
        declarationTD.setNameRecipientCountry(declarationDTO.getCountryNameDestination());
        declarationTD.setIdentification(declarationDTO.getVehicleCount());
        declarationTD.setVehicleRegistrationCountry(declarationDTO.getVehicleNumbers());
        declarationTD.setCodeContiner(declarationDTO.getConteiner());
        declarationTD.setUpCode(declarationDTO.getCodeYP());
        declarationTD.setConditionsOfDeliveryName(declarationDTO.getNameYP());
        declarationTD.setNumberOfVehicles(declarationDTO.getVehicleCountOnBorder());
        if (declarationDTO.isFreeDeliveryCheckbox()) // условие надо прописать, хотя мне до сих пор не понятно зачем оно
        declarationTD.setCurrency(declarationDTO.getCurrencyCode());
        declarationTD.setAccountTotalAmount(declarationDTO.getInvoiceAmount());
        declarationTD.setCurrencyRate(new CurrencyRate(declarationDTO.getCurrencyRateDTO().getDate(),
                declarationDTO.getCurrencyRateDTO().getCurrencyRate(),
                declarationDTO.getCurrencyRateDTO().getEuroRate(),
                declarationDTO.getCurrencyRateDTO().getUsdRate()));
        declarationTD.setDealCode(declarationDTO.getTransactionCharacterCode());
        declarationTD.setSpecialEconomicDealCode(declarationDTO.getForeignTradeFeatureCode());

        declarationTD.setCodeTransport(declarationDTO.getBorderTransportTypeCode());
        declarationTD.setCodeTransportInCountry(declarationDTO.getDomesticTransportTypeCode());
        declarationTD.setTotalGrossWeight(declarationDTO.getGrossWeight());
        declarationTD.setTotalNetWeight(declarationDTO.getNetWeight());
        declarationTD.setCustomsBorderCode(declarationDTO.getCustomsCode());
        declarationTD.setCustomsBorderDescription(declarationDTO.getLargeTextArea());
        declarationTD.setProductDescription(declarationDTO.getProductDescription());
        declarationTD.setProductLocation(new ProductLocation(declarationDTO.getProductLocationDTO().getUzoRegistry(),
                declarationDTO.getProductLocationDTO().getCustomsCode(),
                declarationDTO.getProductLocationDTO().getType(),
                declarationDTO.getProductLocationDTO().getQuantity(),
                declarationDTO.getProductLocationDTO().getDocumentNumber(),
                declarationDTO.getProductLocationDTO().getDate(),
                declarationDTO.getProductLocationDTO().getZtkNumber(),
                declarationDTO.getProductLocationDTO().getTransportType(),
                declarationDTO.getProductLocationDTO().getVehicleNumber(),
                declarationDTO.getProductLocationDTO().getStationOrPort(),
                declarationDTO.getProductLocationDTO().getCountry(),
                declarationDTO.getProductLocationDTO().getPostalCode(),
                declarationDTO.getProductLocationDTO().getRegionOrDistrict(),
                declarationDTO.getProductLocationDTO().getLocality(),
                declarationDTO.getProductLocationDTO().getHouseNumber()));
        declarationTD = declarationTDRepository.save(declarationTD);
        List<Product>  productList = new ArrayList<>();
        for(ProductDTO pr: declarationDTO.getProductDTOS()){
            Product product = new Product();
            product.setItemNumber(pr.getItemNumber());
            product.setProductCode(pr.getProductCode());
            product.setOriginCountryCode(pr.getOriginCountryCode());
            product.setGrossWeight(pr.getGrossWeight());
            product.setPreference(pr.getPreference());
            product.setProcedure(pr.getProcedure());
            product.setNetWeight(pr.getNetWeight());
            product.setQuota(pr.getQuota());
            product.setDeclarationTD(declarationTD);
            productList.add(productRepository.save(product));
        }
        declarationTD.setProductList(productList);
        declarationTDRepository.save(declarationTD);
    }
}
