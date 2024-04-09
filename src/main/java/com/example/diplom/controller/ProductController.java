package com.example.diplom.controller;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestParam("name") String name,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("count") int count,
                                                    @RequestParam("winter") boolean winter,
                                                    @RequestParam("summer") boolean summer,
                                                    @RequestParam("images") MultipartFile[] images) throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setCount(count);
        productDto.setWinter(winter);
        productDto.setSummer(summer);
        productDto.setImages(productService.uploadImages(images));

        ProductDto savedProduct = productService.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}
