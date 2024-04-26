package com.example.diplom.dto;

public class ProductPrice {
    private Long productId;
    private Integer price;

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getPrice() {
        return price;
    }
}
