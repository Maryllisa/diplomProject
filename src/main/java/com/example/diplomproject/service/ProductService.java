package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
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
}
