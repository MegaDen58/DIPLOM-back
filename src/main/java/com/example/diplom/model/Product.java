package com.example.diplom.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    private List<String> images;

    private String description;

    private Integer count;

    private Integer price;

    private boolean winter;

    private boolean summer;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", description='" + description + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", winter=" + winter +
                ", summer=" + summer +
                '}';
    }
}
