package com.example.diplom.controller;

import com.example.diplom.model.Order;
import com.example.diplom.model.Product;
import com.example.diplom.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/items")
    public ResponseEntity<List<Integer>> getItemsByUserId(@PathVariable Long userId) {
        List<Integer> items = orderService.getItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/delete/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrderById(orderId);
        if (deleted) {
            return ResponseEntity.ok("Order with ID " + orderId + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + orderId + " not found.");
        }
    }

    @PutMapping("/updateType")
    public ResponseEntity<String> updateOrderType(@RequestBody Map<String, Object> payload) {
        Long orderId = ((Number) payload.get("orderId")).longValue();
        String type = (String) payload.get("type");
        orderService.updateOrderType(orderId, type);
        return ResponseEntity.ok("Order type updated successfully");
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByOrderId(@PathVariable Long id) {
        List<Product> products = orderService.getProductsFromUserCart(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
