package com.example.diplom.service;

import com.example.diplom.model.Order;
import com.example.diplom.model.Product;
import com.example.diplom.model.User;
import com.example.diplom.repository.OrderRepository;
import com.example.diplom.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Integer> getItemsByUserId(Long userId) {
        return orderRepository.findItemsByUserId(userId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_id(userId);
    }

    public List<Product> getProductsFromUserCart(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            List<Integer> favoriteIds = order.getItems();
            List<Product> cartProducts = new ArrayList<>();
            for (Integer productId : favoriteIds) {
                Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(() -> new RuntimeException("Product not found"));
                cartProducts.add(product);
            }
            return cartProducts;
        } else {
            return new ArrayList<>();
        }
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }
}
