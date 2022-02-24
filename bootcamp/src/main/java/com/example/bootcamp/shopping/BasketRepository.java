package com.example.bootcamp.shopping;

import com.example.bootcamp.shopping.entities.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

    List<Basket> findByUserId(int userId);

}
