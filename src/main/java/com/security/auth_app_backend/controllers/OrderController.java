package com.security.auth_app_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.security.auth_app_backend.dtos.CheckoutRequest;
import com.security.auth_app_backend.entities.Order;
import com.security.auth_app_backend.services.OrderService;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/checkout")
    public ResponseEntity<Order> checkout(@RequestBody CheckoutRequest request) {
        Order order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }
}
