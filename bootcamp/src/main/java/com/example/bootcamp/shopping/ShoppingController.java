package com.example.bootcamp.shopping;

import com.example.bootcamp.shopping.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @PostMapping("/basket")
    public BasketItemResponse addProductToBasket(@Valid @RequestBody BasketRequest body) {
        return shoppingService.createBasket(body);
    }

    @GetMapping("/basket/{userId}")
    public BasketResponse getBasket(@PathVariable int userId) {
        return shoppingService.getBasket(userId);
    }

}
