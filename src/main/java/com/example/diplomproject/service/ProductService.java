package com.example.diplomproject.service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import com.example.diplomproject.model.dto.DeliveryProductDTO;
import com.example.diplomproject.model.dto.ProductDTO;
import com.example.diplomproject.model.dto.SearchData;
import com.example.diplomproject.model.entity.ApplicationForStorage;
import com.example.diplomproject.model.entity.Product;
import com.example.diplomproject.model.entity.declaration.DeclarationTD;
import com.example.diplomproject.repository.ApplicationForReleaseRepository;
import com.example.diplomproject.repository.DeclarationTDRepository;
import com.example.diplomproject.repository.ProductRepository;
import com.example.diplomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final EntityManager entityManager;private final ProductRepository productRepository;
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

    public List<Product> getAllProductByApplication(Long id) {
        return productRepository.getAllByApplication(id);
    }

    public Map<String, String> check(BindingResult result, DeliveryProductDTO deliveryProductDTO) {
        return null;
    }

    public List<Product> getAllProductTrue(String login) {
        return productRepository.findAllByIsDelivery(true);
    }

    public List<ProductDTO> getAllProduct(String name, SearchData searchData) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root);

        List<Order> orders = new ArrayList<>();

        if (searchData.getSortCriteria() != null && !searchData.getSortCriteria().isEmpty()) {
            if (searchData.getHowSort().equals("asc")) {
                switch (searchData.getSortCriteria()) {
                    case "numberDeclaration":
                        orders.add(builder.asc(root.get("numberDeclaration")));
                        break;
                    case "nameProduct":
                        orders.add(builder.asc(root.get("nameProduct")));
                        break;
                    case "productCode":
                        orders.add(builder.asc(root.get("productCode")));
                        break;
                    case "grossWeight":
                        orders.add(builder.asc(root.get("grossWeight")));
                        break;
                    case "netWeight":
                        orders.add(builder.asc(root.get("netWeight")));
                        break;
                }
            } else {
                switch (searchData.getSortCriteria()) {
                    case "numberDeclaration":
                        orders.add(builder.desc(root.get("numberDeclaration")));
                        break;
                    case "nameProduct":
                        orders.add(builder.desc(root.get("nameProduct")));
                        break;
                    case "productCode":
                        orders.add(builder.desc(root.get("productCode")));
                        break;
                    case "grossWeight":
                        orders.add(builder.desc(root.get("grossWeight")));
                        break;
                    case "netWeight":
                        orders.add(builder.desc(root.get("netWeight")));
                        break;
                }
            }
        }

        if (!orders.isEmpty()) {
            query.orderBy(orders);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (searchData.getSearchQuery() != null && !searchData.getSearchQuery().isEmpty()) {
            switch (searchData.getSearchParam()) {
                case "numberDeclaration":
                    predicates.add(builder.like(root.get("idApplication"), searchData.getSearchQuery()));
                    break;
                case "nameProduct":
                    predicates.add(builder.like(root.get("nameProduct"), searchData.getSearchQuery()));
                    break;
                case "productCode":
                    predicates.add(builder.like(root.get("productCode"), searchData.getSearchQuery()));
                    break;
                case "grossWeight":
                    predicates.add(builder.like(root.get("grossWeight"), searchData.getSearchQuery()));
                    break;
                case "netWeight":
                    predicates.add(builder.like(root.get("netWeight"), searchData.getSearchQuery()));
                    break;

            }
        }


        Predicate searchPredicate = builder.and(predicates.toArray(new Predicate[0]));
        query.where(searchPredicate);
        predicates.add(builder.equal(root.get("account"), userRepository.findByLogin(name)));
        query.where(searchPredicate);
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);

        List<Product> products = typedQuery.getResultList();
        List<ProductDTO> productDTOList = new ArrayList<>();
        products.forEach(x->{
            productDTOList.add(x.build());
        });

        return productDTOList;
    }
}
