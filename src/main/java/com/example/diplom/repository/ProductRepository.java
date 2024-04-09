package com.example.diplom.repository;

import com.example.diplom.dto.ProductDto;
import com.example.diplom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(int id);
}
