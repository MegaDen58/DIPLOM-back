package com.example.diplom.repository;

import com.example.diplom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(int id);
    @Query("SELECT p FROM Product p WHERE :image MEMBER OF p.images")
    Product findProductByImage(String image);
}
