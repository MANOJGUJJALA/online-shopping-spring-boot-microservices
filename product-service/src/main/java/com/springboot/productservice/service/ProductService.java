package com.springboot.productservice.service;


import com.springboot.productservice.dto.ProductRequest;
import com.springboot.productservice.dto.ProductResponse;
import com.springboot.productservice.model.Product;
import com.springboot.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createproduct(ProductRequest productRequest){
        Product product=Product.builder()

                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is created",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products=productRepository.findAll();

      return products.stream().map(product -> getproductResponse(product)).collect(Collectors.toList());

    }

    private ProductResponse getproductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
