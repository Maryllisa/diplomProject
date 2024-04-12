package com.example.diplomproject.service;

import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> addNewProduct(DeclarationTD declarationTDForDB, List<ProductDTO> productList){
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productList){
            Product productForDB = productDTO.build();
            productForDB.setDeclarationTD(declarationTDForDB);
            products.add(productRepository.save(productForDB));
        }
        return products;
    }
}
