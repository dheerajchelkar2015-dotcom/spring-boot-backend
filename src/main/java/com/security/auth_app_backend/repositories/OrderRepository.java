package com.security.auth_app_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.auth_app_backend.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

