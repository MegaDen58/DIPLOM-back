package com.example.diplom.service;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductDto saveProduct(ProductDto productDto) {
        return productRepository.save(productDto);
    }
}
