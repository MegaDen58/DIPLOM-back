package com.example.diplom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private List<String> images;
    private String description;
    private boolean winter;
    private boolean summer;
    private Integer price;
    private String color;
    private String material;
    private String size;
    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", description='" + description + '\'' +
                ", winter=" + winter +
                ", summer=" + summer +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", material='" + material + '\'' +
                ", size='" + size + '\'' +
                '}';
    }
}
