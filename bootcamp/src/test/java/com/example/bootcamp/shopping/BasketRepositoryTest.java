package com.example.bootcamp.shopping;

import com.example.bootcamp.shopping.entities.Basket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BasketRepositoryTest {

    @Autowired
    private BasketRepository basketRepository;

    @Test
    void findByUserId() {
        Basket basket = new Basket(20001, 10001, 15000, 1);
        Basket basket2 = new Basket(20001, 10002, 5000, 1);
        basketRepository.save(basket);
        basketRepository.save(basket2);

        Basket basket3 = new Basket(20002, 10002, 5000, 1);
        basketRepository.save(basket3);

        List<Basket> result = basketRepository.findByUserId(20001);

        assertTrue(result.stream().filter(f -> f.getUserId() == 20001).count() == 2);
    }
}