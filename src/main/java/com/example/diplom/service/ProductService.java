package com.example.diplom.service;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.model.Product;
import com.example.diplom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductDto productDto = convertToDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public Product saveProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return productRepository.save(savedProduct);
    }

    public List<String> uploadImages(MultipartFile[] files) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            file.transferTo(new File(filePath));
            imagePaths.add(fileName);
        }
        return imagePaths;
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCount(product.getCount());
        productDto.setWinter(product.isWinter());
        productDto.setSummer(product.isSummer());
        productDto.setImages(product.getImages());
        return productDto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCount(productDto.getCount());
        product.setWinter(productDto.isWinter());
        product.setSummer(productDto.isSummer());
        product.setImages(productDto.getImages());
        return product;
    }
}
