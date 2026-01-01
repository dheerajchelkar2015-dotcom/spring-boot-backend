package com.security.auth_app_backend.services.impl;


import org.springframework.stereotype.Service;

import com.security.auth_app_backend.dtos.CartItemDTO;
import com.security.auth_app_backend.dtos.CheckoutRequest;
import com.security.auth_app_backend.entities.Order;
import com.security.auth_app_backend.entities.OrderItem;
import com.security.auth_app_backend.repositories.OrderRepository;
import com.security.auth_app_backend.services.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(CheckoutRequest request) {

        Order order = new Order();
        order.setUserEmail(request.getUserEmail());
        order.setTotalAmount(request.getTotalAmount());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = request.getItems()
                .stream()
                .map(this::mapToOrderItem)
                .toList();

        orderItems.forEach(item -> item.setOrder(order));
        order.setItems(orderItems);

        return orderRepository.save(order);
    }

    private OrderItem mapToOrderItem(CartItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setProductId(dto.getProductId());
        item.setProductName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return item;
    }
}
