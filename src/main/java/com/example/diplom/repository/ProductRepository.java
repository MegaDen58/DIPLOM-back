package com.example.diplom.repository;

import com.example.diplom.dto.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDto, Long> {
    ProductDto findProductById(int id);
}
