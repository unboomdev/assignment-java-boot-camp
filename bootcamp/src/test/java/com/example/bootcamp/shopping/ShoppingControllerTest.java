package com.example.bootcamp.shopping;

import com.example.bootcamp.common.ExceptionResponse;
import com.example.bootcamp.payment.Payment;
import com.example.bootcamp.payment.PaymentRepository;
import com.example.bootcamp.product.ProductRepository;
import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.shopping.entities.Basket;
import com.example.bootcamp.shopping.entities.OrderDetail;
import com.example.bootcamp.shopping.model.*;
import com.example.bootcamp.user.UserRepository;
import com.example.bootcamp.user.entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private BasketRepository basketRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private OrderDetailRepository orderDetailRepository;

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าสำเร็จ จะได้รับข้อความ Update success")
    void case01() {
        // Arrange
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));

        Product product1 = new Product(10001, "Adidas NMD R1 PK Japan Triple Black", 15000, 14, "Comfort");
        when(productRepository.findById(10001)).thenReturn(Optional.of(product1));

        BasketRequest basketRequest = new BasketRequest(20001, 10001, 1);

        // Act
        BasketItemResponse result = testRestTemplate.postForObject("/basket", basketRequest, BasketItemResponse.class);

        // Assert
        assertEquals("Update success", result.getMessage());
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่งค่า userId ที่ไม่มีในระบบ จะได้รับข้อความ User not found")
    void case02() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        BasketRequest basketRequest = new BasketRequest(999, 10001, 1);

        // Act
        ExceptionResponse result = testRestTemplate.postForObject("/basket", basketRequest, ExceptionResponse.class);

        // Assert
        assertEquals("User not found", result.getMessage());
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่งค่า productId ที่ไม่มีในระบบ จะได้รับข้อความ Product not found")
    void case03() {
        // Arrange
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        BasketRequest basketRequest = new BasketRequest(20001, 999, 1);

        // Act
        ExceptionResponse result = testRestTemplate.postForObject("/basket", basketRequest, ExceptionResponse.class);

        // Assert
        assertEquals("Product not found", result.getMessage());
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่ง quantity = 0 จะได้รับข้อความ Quantity must be least one")
    void case04() {
        // Arrange
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));

        Product product1 = new Product(10001, "Adidas NMD R1 PK Japan Triple Black", 15000, 14, "Comfort");
        when(productRepository.findById(10001)).thenReturn(Optional.of(product1));

        BasketRequest basketRequest = new BasketRequest(20001, 10001, 0);

        // Act
        ExceptionResponse result = testRestTemplate.postForObject("/basket", basketRequest, ExceptionResponse.class);

        // Assert
        assertEquals("Quantity must be least one", result.getMessage());
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่ง userId = 20001 พบข้อมูล")
    void case05() {
        Product product1 = new Product(10001, "Adidas NMD R1 PK Japan", 15000, 14, "Comfort");
        when(productRepository.findById(10001)).thenReturn(Optional.of(product1));

        List<Basket> baskets = new ArrayList<>();
        baskets.add(new Basket(20001, 10001, 15000, 1));
        when(basketRepository.findByUserId(20001)).thenReturn(baskets);

        BasketResponse result = testRestTemplate.getForObject("/basket/20001", BasketResponse.class);

        assertTrue(!result.getProducts().isEmpty());
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่ง userId = 20001 ไม่พบข้อมูล")
    void case06() {
        when(basketRepository.findByUserId(20001)).thenReturn(new ArrayList<>());

        BasketResponse result = testRestTemplate.getForObject("/basket/20001", BasketResponse.class);

        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อสำเร็จ, msg = Update success")
    void checkout() {
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));

        Payment payment = new Payment(30002, "เก็บปลายทาง");
        when(paymentRepository.findById(30002)).thenReturn(Optional.of(payment));

        Basket basket = new Basket(20001, 10001, 15000, 1);
        basket.setId(1);
        when(basketRepository.findById(1)).thenReturn(Optional.of(basket));

        List<CheckoutItem> items = new ArrayList<>();
        CheckoutItem item1 = new CheckoutItem();
        item1.setBasketId(1);
        items.add(item1);

        CheckoutRequest request = new CheckoutRequest();
        request.setPaymentId(30002);
        request.setUserId(20001);
        request.setItems(items);

        BasketItemResponse result = testRestTemplate.postForObject("/checkout", request, BasketItemResponse.class);

        assertEquals("Update success", result.getMessage());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่งค่าของ items เป็น empty, msg = Item must not be empty")
    void checkout02() {
        List<CheckoutItem> items = new ArrayList<>();
        CheckoutRequest request = new CheckoutRequest();
        request.setPaymentId(30002);
        request.setUserId(20001);
        request.setItems(items);

        ExceptionResponse result = testRestTemplate.postForObject("/checkout", request, ExceptionResponse.class);

        assertEquals("Item must not be empty", result.getMessage());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง userId ที่ไม่มีในระบบ, msg = User not found")
    void checkout03() {
        when(userRepository.findById(99999)).thenReturn(Optional.empty());

        List<CheckoutItem> items = new ArrayList<>();
        CheckoutItem item1 = new CheckoutItem();
        item1.setBasketId(1);
        items.add(item1);

        CheckoutRequest request = new CheckoutRequest();
        request.setPaymentId(30002);
        request.setUserId(99999);
        request.setItems(items);

        ExceptionResponse result = testRestTemplate.postForObject("/checkout", request, ExceptionResponse.class);

        assertEquals("User not found", result.getMessage());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง paymentId ที่ไม่มีในระบบ, msg = Payment not found")
    void checkout04() {
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));

        when(paymentRepository.findById(99999)).thenReturn(Optional.empty());

        List<CheckoutItem> items = new ArrayList<>();
        CheckoutItem item1 = new CheckoutItem();
        item1.setBasketId(1);
        items.add(item1);

        CheckoutRequest request = new CheckoutRequest();
        request.setPaymentId(99999);
        request.setUserId(20001);
        request.setItems(items);

        ExceptionResponse result = testRestTemplate.postForObject("/checkout", request, ExceptionResponse.class);

        assertEquals("Payment not found", result.getMessage());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง basketId ที่ไม่มีในระบบ, msg = Basket's item not found")
    void checkout05() {
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));

        Payment payment = new Payment(30002, "เก็บปลายทาง");
        when(paymentRepository.findById(30002)).thenReturn(Optional.of(payment));

        when(basketRepository.findById(99999)).thenReturn(Optional.empty());

        List<CheckoutItem> items = new ArrayList<>();
        CheckoutItem item1 = new CheckoutItem();
        item1.setBasketId(99999);
        items.add(item1);

        CheckoutRequest request = new CheckoutRequest();
        request.setPaymentId(30002);
        request.setUserId(20001);
        request.setItems(items);

        ExceptionResponse result = testRestTemplate.postForObject("/checkout", request, ExceptionResponse.class);

        assertEquals("Basket's item not found", result.getMessage());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง order_id = 1 พบข้อมูล")
    void getOrderSummary() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setUserId(20001);
        orderDetail.setPaymentId(30001);
        orderDetail.setInvoiceNumber(new Random().nextInt(10));
        orderDetail.setOrderDate(new Date());
        orderDetail.setPaidDate(null);
        orderDetail.setPaymentExpireDate(null);
        when(orderDetailRepository.findById(1)).thenReturn(Optional.of(orderDetail));

        OrderDetail result = testRestTemplate.getForObject("/order/summary/1", OrderDetail.class);

        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง order_id = 1 ไม่พบข้อมูล และได้ข้อความกลับมา, msg = Order summary not found")
    void getOrderSummary02() {
        when(orderDetailRepository.findById(1)).thenReturn(Optional.empty());

        ExceptionResponse result = testRestTemplate.getForObject("/order/summary/1", ExceptionResponse.class);

        assertEquals("Order summary not found", result.getMessage());
    }

}