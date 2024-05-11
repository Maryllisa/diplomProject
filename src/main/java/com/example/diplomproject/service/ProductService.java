package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.repository.ApplicationForReleaseRepository;
import com.example.diplomproject.repository.DeclarationTDRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DeclarationTDRepository declarationTDRepository;
    private final ApplicationForReleaseRepository applicationForReleaseRepository;
    public List<Product> addNewProduct(DeclarationTD declarationTDForDB, List<ProductDTO> productList){
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productList){
            Product productForDB = productDTO.build();
            productForDB.setDeclarationTD(declarationTDForDB);
            products.add(productRepository.save(productForDB));
        }
        return products;
    }

    public List<ProductDTO> getAllProduct(String login) {
        List<Product> products = productRepository.findAllByDeclarationAndAccount(userRepository.findByLogin(login));
        List<ProductDTO> productDTOList = new ArrayList<>();
        products.forEach(x->{
            productDTOList.add(x.build());
        });

        return productDTOList;
    }

    public List<Product> getAllProductByDeclaration(Long declarationId) {
        return productRepository.findAllByDeclarationTD(declarationTDRepository.getById(declarationId));
    }

    public void updateProduct(List<Product> productList, Long id) {
        DeclarationTD declarationTD = declarationTDRepository.getById(id);
        for (Product p: productList) {
            declarationTD.getProductList().forEach(data -> {
                if (data.getNameProduct().equals(p.getNameProduct())){
                    Product product = productRepository.findById(data.getIdProduct()).orElse(null);
                    if (product != null) {
                        product.setProductCode(p.getProductCode());
                        product.setQuota(p.getQuota());
                        product.setPreference(p.getPreference());
                        product.setProcedure(p.getProcedure());
                        product.setNameProduct(p.getNameProduct());
                        product.setNetWeight(p.getNetWeight());
                        product.setGrossWeight(p.getGrossWeight());
                        product.setDate(p.getDate());
                        product.setFinalDate(p.getFinalDate());
                        product.setOriginCountryCode(p.getOriginCountryCode());
                        productRepository.save(product);
                    }
                }
            });
        }
    }

    public List<ProductDTO> getAllProductByApplication(String name) {
        List<Product> products = productRepository.findAllByApplicationForReleaseIsNull();
        List<ProductDTO> productDTOList = new ArrayList<>();
        products.forEach(x->{
            productDTOList.add(x.build());
        });
        return productDTOList;
    }

    public ProductDTO findById(Long id) {
        return productRepository.getById(id).build();
    }
}
