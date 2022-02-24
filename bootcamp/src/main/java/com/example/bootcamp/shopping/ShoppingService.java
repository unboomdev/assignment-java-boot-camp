package com.example.bootcamp.shopping;

import com.example.bootcamp.product.ProductRepository;
import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.exception.ProductNotFoundException;
import com.example.bootcamp.shopping.entities.Basket;
import com.example.bootcamp.shopping.exception.QuantityMustBeLeastOneException;
import com.example.bootcamp.shopping.model.*;
import com.example.bootcamp.user.UserRepository;
import com.example.bootcamp.user.entities.User;
import com.example.bootcamp.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setBasketRepository(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private int calculatePrice(List<Basket> items) {
        int totalPrice = 0;
        for (Basket item : items) {
            totalPrice += (item.getProductPrice() * item.getQuantity());
        }
        return totalPrice;
    }

    public BasketItemResponse createBasket(BasketRequest basketRequest) {
        Optional<User> user = userRepository.findById(basketRequest.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<Product> product = productRepository.findById(basketRequest.getProductId());
        if (!product.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }

        if (basketRequest.getQuantity() < 1) {
            throw new QuantityMustBeLeastOneException("Quantity must be least one");
        }

        Basket basket = new Basket(basketRequest.getUserId(), basketRequest.getProductId(), product.get().getPrice(), basketRequest.getQuantity());
        basketRepository.save(basket);
        return new BasketItemResponse(basket.getId(), "Update success");
    }

    public BasketResponse getBasket(int userId) {
        List<Basket> result = basketRepository.findByUserId(userId);
        List<BasketItem> products = new ArrayList<>();
        for (Basket basket : result) {
            Optional<Product> productResult  = productRepository.findById(basket.getProductId());
            if (productResult.isPresent()) {
                Product product = productResult.get();
                BasketItem item = new BasketItem(basket.getId(), product.getId(), product.getName(), product.getPrice(), basket.getQuantity());
                products.add(item);
            }
        }
        return new BasketResponse(calculatePrice(result), products);
    }

}
