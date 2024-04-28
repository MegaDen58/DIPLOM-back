package com.example.diplom.controller;

import com.example.diplom.model.Order;
import com.example.diplom.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}/items")
    public ResponseEntity<List<Integer>> getItemsByUserId(@PathVariable Long userId) {
        List<Integer> items = orderService.getItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}
