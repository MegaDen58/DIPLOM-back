package com.example.diplom.service;

import com.example.diplom.model.Order;
import com.example.diplom.model.Product;
import com.example.diplom.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Integer> getItemsByUserId(Long userId) {
        return orderRepository.findItemsByUserId(userId);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_id(userId);
    }

    public List<Product> getAllItemsById(Long id){
        return orderRepository.findProductsByOrderId(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }
}
