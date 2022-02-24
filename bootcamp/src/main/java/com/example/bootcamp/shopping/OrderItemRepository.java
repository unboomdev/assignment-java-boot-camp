package com.example.bootcamp.shopping;

import com.example.bootcamp.shopping.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
