package com.security.auth_app_backend.services;

import com.security.auth_app_backend.dtos.CheckoutRequest;
import com.security.auth_app_backend.entities.Order;

public interface OrderService {
    Order placeOrder(CheckoutRequest request);
}
