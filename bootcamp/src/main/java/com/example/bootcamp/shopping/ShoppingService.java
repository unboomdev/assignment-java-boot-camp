package com.example.bootcamp.shopping;

import com.example.bootcamp.payment.Payment;
import com.example.bootcamp.payment.PaymentRepository;
import com.example.bootcamp.product.ProductRepository;
import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.exception.ProductNotFoundException;
import com.example.bootcamp.shopping.entities.Basket;
import com.example.bootcamp.shopping.entities.OrderDetail;
import com.example.bootcamp.shopping.entities.OrderItem;
import com.example.bootcamp.shopping.exception.DataNotFoundException;
import com.example.bootcamp.shopping.exception.ProductsItemEmptyException;
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
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public void setPaymentRepository(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setBasketRepository(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
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

    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {
        if (checkoutRequest.getItems().isEmpty()) {
            throw new ProductsItemEmptyException("Item must not be empty");
        }

        Optional<User> user = userRepository.findById(checkoutRequest.getUserId());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }

        Optional<Payment> paymentResult = paymentRepository.findById(checkoutRequest.getPaymentId());
        if (!paymentResult.isPresent()) {
            throw new DataNotFoundException("Payment not found");
        }

        List<Basket> baskets = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        for (CheckoutItem item : checkoutRequest.getItems()) {
            Optional<Basket> basket = basketRepository.findById(item.getBasketId());
            if (!basket.isPresent()) {
                throw new DataNotFoundException("Basket's item not found");
            }
            OrderItem orderItem = new OrderItem(basket.get().getProductId(), basket.get().getProductPrice(), basket.get().getQuantity());
            orderItems.add(orderItem);
            baskets.add(basket.get());
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_YEAR, 1);

        OrderDetail order = new OrderDetail();
        order.setUserId(checkoutRequest.getUserId());
        order.setPaymentId(checkoutRequest.getPaymentId());
        order.setInvoiceNumber(new Random().nextInt(10));
        order.setTotalPrice(calculatePrice(baskets));
        order.setOrderDate(new Date());
        order.setPaidDate(null);
        order.setPaymentExpireDate(checkoutRequest.getPaymentId() == 30003 ? c.getTime() : null); // ให้ 30003 = ชำระเงินผ่านเคาน์เตอร์
        orderDetailRepository.save(order);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
        }
        System.out.println(orderItems.get(0).getId());
        orderItemRepository.saveAll(orderItems);
        basketRepository.deleteAll(baskets);
        return new CheckoutResponse(order.getId(), "Update success");
    }

    public OrderDetail getOrderSummary(int orderId) {
        Optional<OrderDetail> result = orderDetailRepository.findById(orderId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new DataNotFoundException("Order summary not found");
    }
}
