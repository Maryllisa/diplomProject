package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ApplicationForStorageDTO;
import com.example.diplomproject.model.entity.*;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.model.entity.enumStatus.StatusApplication;
import com.example.diplomproject.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationForStorageService {
    private final ApplicationForStorageRepository applicationForStorageRepository;
    private final UserRepository accountRepository;
    private final GoodTransportDocumentRepository goodTransportDocumentRepository;
    private final DeclarationTDRepository declarationTDRepository;
    private final TruckRepository truckRepository;
    private final CRMRepository crmRepository;

    public void addNewApplication(ApplicationForStorageDTO applicationForStorageDTO, String name) {
        Account account = accountRepository.findByLogin(name);
        ApplicationForStorage applicationForStorage = applicationForStorageDTO.build(StatusApplication.PENDING,
                goodTransportDocumentRepository.getById(applicationForStorageDTO.getIdGoodTransportDocument()),
                declarationTDRepository.getById(applicationForStorageDTO.getIdDeclarationTD()),
                crmRepository.getById(applicationForStorageDTO.getIdCRM()),
                truckRepository.getById(applicationForStorageDTO.getIdTruck()));
        applicationForStorage.setAccount(account);
        applicationForStorageRepository.save(applicationForStorage);
    }

    public List<ApplicationForStorageDTO> getAllApplictionByAccount(String name) {
        List<ApplicationForStorageDTO> applicationForStorageDTOS = new ArrayList<>();
        List<ApplicationForStorage> applicationForStorages = applicationForStorageRepository.findAllByAccount(accountRepository.findByLogin(name));
        applicationForStorages.forEach(x -> {
            applicationForStorageDTOS.add(x.build());
        });
        return applicationForStorageDTOS;
    }

    public Map<String, String> checkApplication(ApplicationForStorageDTO applicationForStorageDTO, BindingResult result) {
        Map<String, String> resultDescription = new HashMap<>();
        result.getFieldErrors().forEach(x -> {
            switch (x.getField()) {
                case "datePost": {
                    resultDescription.put("datePost", "Некорректная дата поставки");
                    break;
                }
                case "dateZav": {
                    resultDescription.put("dateZav", "Некорректная дата подачи заявления");
                    break;
                }
                case "idGoodTransportDocument": {
                    resultDescription.put("idGoodTransportDocument", "Выберите ТТН");
                    break;
                }
                case "idDeclarationTD": {
                    resultDescription.put("idDeclarationTD", "Выберите декларацию");
                    break;
                }
                case "idCRM": {
                    resultDescription.put("idCRM", "Выберите CRM");
                    break;
                }
                case "idTruck": {
                    resultDescription.put("idTruck", "Выберите водитеоля");
                    break;
                }
            }
        });
        return resultDescription;
    }

    public boolean checkGoodTransportDocument(Long id) {
        GoodTransportDocument goodTransportDocument = goodTransportDocumentRepository.getById(id);
        if (goodTransportDocument == null) {
            return false;
        }
        return goodTransportDocument.getGoodsTransportDocumentNumbers() != null
                && goodTransportDocument.getSrcTransportDocument() != null;

    }

    public boolean checkDeclaration(Long id) {
        DeclarationTD declarationTD = declarationTDRepository.getById(id);
        if (declarationTD == null) return false;
        return declarationTD.getDeclarationNumber() != null
                && declarationTD.getIndividuals().check()
                && declarationTD.getFormGr3() != null
                && declarationTD.getSpecification() != null
                && declarationTD.getColProducts() != 0
                && declarationTD.getAllPlace() != null
                && declarationTD.getDeclarationDetails() != null
                && declarationTD.getRecipientAddress().check()
                && declarationTD.getFinancialRegulator() != null
                && declarationTD.getTorgCountry() != null
                && declarationTD.getCost() != 0
                && declarationTD.getIndividuals().check()
                && declarationTD.getCodeSenderCountry() != null
                && declarationTD.getNameSenderCountry() != null
                && declarationTD.getCodeOriginCountry() != null
                && declarationTD.getNameOriginCountry() != null
                && declarationTD.getCodeRecipientCountry() != null
                && declarationTD.getIdentification() != null
                && declarationTD.getVehicleRegistrationCountry() != null
                && declarationTD.getCodeContiner()!=null
                && declarationTD.getUpCode() != null
                && declarationTD.getConditionsOfDeliveryName() != null
                && declarationTD.getNumberOfVehicles() != null
                && declarationTD.getCurrency() != null
                && declarationTD.getAccountTotalAmount() != null
                && declarationTD.getCurrencyRate().check()
                && declarationTD.getDealCode() != null
                && declarationTD.getSpecialEconomicDealCode() != null
                && declarationTD.getCodeTransport() != null
                && declarationTD.getCodeTransportInCountry() != null
                && declarationTD.getTotalGrossWeight() != 0
                && declarationTD.getTotalNetWeight() != 0
                && declarationTD.getCustomsBorderCode()!=null
                && declarationTD.getCustomsBorderDescription() != null
                && declarationTD.getProductLocation().check()
                && declarationTD.getProductDescription() != null
                && !declarationTD.getProductList().isEmpty();
         }
     public boolean checkCRM(Long id){
         CRM crm = crmRepository.getById(id);
         if (crm==null) return false;
         return crm.getSender().check()
                 && crm.getResipient().check()
                 && crm.getCountryWH()!=null
                 && crm.getCityWH()!=null
                 && crm.getStreetWH()!=null
                 && crm.getHouseNumberWH()!=null
                 && crm.getLoadingCountry()!=null
                 && crm.getLoadingCity()!=null
                 && crm.getLoadingStreet()!=null
                 && crm.getLoadingDate()!=null
                 && crm.getInvoiceDocument()!=null
                 && crm.getShippingSpecificationDocument()!=null
                 && crm.getQualityCertificateDocument()!=null
                 && crm.getVeterinaryCertificateDocument()!=null
                 && crm.getQuarantineCertificateDocument()!=null
                 && crm.getCertificateOfOriginDocument()!=null
                 && crm.getLoadingCertificateDocument()!=null
                 && crm.getCargoName()!=null
                 && crm.getNackagingType()!=null
                 && crm.getNumbers()!=null
                 && crm.getStatistikCode()!=null
                 && crm.getGrossWeight()!=null
                 && crm.getVolume()!=null
                 && crm.getCustomsProcessing().check()
                 && crm.getReturnCountry()!=null
                 && crm.getReturnCity()!=null
                 && crm.getReturnStreet()!=null
                 && crm.getReturnHouse()!=null
                 && crm.getPaymentTerms()!=null
                 && crm.getCarrier().check()
                 && crm.getSubsequentCarrier().check()
                 && crm.getCarrierNotes()!=null
                 && crm.getCargoReceivedDate()!=null
                 && crm.getCmrFilledDate()!=null
                 && crm.getTractorRegistrationNumber()!=null
                 && crm.getTrailerRegistrationNumber()!=null;
     }
     public boolean checkTruck(Long id){
         Truck truck = truckRepository.getById(id);
         if (truck == null) return false;
         return truck.getYearTruck()!=0
                 && truck.getBrand()!=null
                 && truck.getModel()!=null
                 && truck.getDriver().check()
                 && truck.getRegistrationNumber()!=null;
     }

    public Map<String, String> checkApplication(ApplicationForStorageDTO applicationForStorageDTO) {
        Map<String, String> descriptionError = new HashMap<>();
        if (!checkGoodTransportDocument(applicationForStorageDTO.getIdGoodTransportDocument())){
            descriptionError.put("goodTransportDocument", "Выбранный ТТН не заполнен полностью");
        }
        if (!checkCRM(applicationForStorageDTO.getIdCRM())){
            descriptionError.put("crm", "Выбранный CRM не заполнен полностью");
        }
        if (!checkDeclaration(applicationForStorageDTO.getIdDeclarationTD())){
            descriptionError.put("declaration", "Выбранная декларация не заполнен полностью");
        }
        if (!checkTruck(applicationForStorageDTO.getIdTruck())){
            descriptionError.put("truck", "Выбранный водитель не зарегистрирован");
        }
        if (!сheckSupplier(applicationForStorageDTO.getIdDeclarationTD(), applicationForStorageDTO.getIdCRM())){
            descriptionError.put("supplier", "Поставщики в CRM и товарно-транспортной декларации не совпадают");
        }
        return descriptionError;

    }

    private boolean сheckSupplier(Long idDeclarationTD, Long idCRM) {
        DeclarationTD declarationTD = declarationTDRepository.getById(idDeclarationTD);
        CRM crm = crmRepository.getById(idCRM);
        if (declarationTD==null || crm==null) return false;
        if (declarationTD.getIndividuals().getIdSupplier().equals(crm.getSender().getIdSupplier())){
            return true;
        }
        return false;
    }
}
