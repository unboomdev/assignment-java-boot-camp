package com.example.bootcamp.shopping;

import com.example.bootcamp.shopping.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}
