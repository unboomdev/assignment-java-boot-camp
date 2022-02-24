package com.example.bootcamp.shopping;

import com.example.bootcamp.payment.Payment;
import com.example.bootcamp.payment.PaymentRepository;
import com.example.bootcamp.product.ProductRepository;
import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.exception.ProductNotFoundException;
import com.example.bootcamp.shopping.entities.Basket;
import com.example.bootcamp.shopping.entities.OrderDetail;
import com.example.bootcamp.shopping.exception.DataNotFoundException;
import com.example.bootcamp.shopping.exception.ProductsItemEmptyException;
import com.example.bootcamp.shopping.exception.QuantityMustBeLeastOneException;
import com.example.bootcamp.shopping.model.*;
import com.example.bootcamp.user.UserRepository;
import com.example.bootcamp.user.entities.User;
import com.example.bootcamp.user.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

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
        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setUserRepository(userRepository);
        shoppingService.setProductRepository(productRepository);
        BasketItemResponse result = shoppingService.createBasket(basketRequest);

        // Assert
        assertEquals("Update success", result.getMessage());
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่งค่า userId ที่ไม่มีในระบบจะ throw UserNotFoundException")
    void case02() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        BasketRequest basketRequest = new BasketRequest(999, 10001, 2);

        // Act
        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);

        // Assert
        assertThrows(UserNotFoundException.class, () -> shoppingService.createBasket(basketRequest));
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่งค่า productId ที่ไม่มีในระบบจะ throw ProductNotFoundException")
    void case03() {
        // Arrange
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        BasketRequest basketRequest = new BasketRequest(20001, 999, 2);

        // Act
        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);
        shoppingService.setProductRepository(productRepository);

        // Assert
        assertThrows(ProductNotFoundException.class, () -> shoppingService.createBasket(basketRequest));
    }

    @Test
    @DisplayName("เพิ่มสินค้าลงตะกร้าแล้วส่งค่า quantity = 0 จะ throw QuantityMustBeLeastOneException")
    void case04() {
        // Arrange
        User user = new User(20001, "john345@test.com", "John", "Judd");
        when(userRepository.findById(20001)).thenReturn(Optional.of(user));

        Product product1 = new Product(10001, "Adidas NMD R1 PK Japan Triple Black", 15000, 14, "Comfort");
        when(productRepository.findById(10001)).thenReturn(Optional.of(product1));

        BasketRequest basketRequest = new BasketRequest(20001, 10001, 0);

        // Act
        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setUserRepository(userRepository);
        shoppingService.setProductRepository(productRepository);

        // Assert
        assertThrows(QuantityMustBeLeastOneException.class, () -> shoppingService.createBasket(basketRequest));
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

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);
        shoppingService.setPaymentRepository(paymentRepository);
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setOrderDetailRepository(orderDetailRepository);
        shoppingService.setOrderItemRepository(orderItemRepository);

        CheckoutResponse result = shoppingService.checkout(request);

        assertEquals("Update success", result.getMessage());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่งค่าของ items เป็น empty, throw: ProductsItemEmptyException")
    void checkout02() {
        List<CheckoutItem> items = new ArrayList<>();
        CheckoutRequest request = new CheckoutRequest();
        request.setPaymentId(30002);
        request.setUserId(20001);
        request.setItems(items);

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);
        shoppingService.setPaymentRepository(paymentRepository);
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setOrderDetailRepository(orderDetailRepository);
        shoppingService.setOrderItemRepository(orderItemRepository);

        assertThrows(ProductsItemEmptyException.class, () -> shoppingService.checkout(request));
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่งส่ง userId ที่ไม่มีในระบบ, throw: UserNotFoundException")
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

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);
        shoppingService.setPaymentRepository(paymentRepository);
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setOrderDetailRepository(orderDetailRepository);
        shoppingService.setOrderItemRepository(orderItemRepository);

        assertThrows(UserNotFoundException.class, () -> shoppingService.checkout(request));
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่งส่ง paymentId ที่ไม่มีในระบบ, throw: DataNotFoundException")
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

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);
        shoppingService.setPaymentRepository(paymentRepository);
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setOrderDetailRepository(orderDetailRepository);
        shoppingService.setOrderItemRepository(orderItemRepository);

        assertThrows(DataNotFoundException.class, () -> shoppingService.checkout(request));
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่งส่ง basketId ที่ไม่มีในระบบ, throw: DataNotFoundException")
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

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setUserRepository(userRepository);
        shoppingService.setPaymentRepository(paymentRepository);
        shoppingService.setBasketRepository(basketRepository);
        shoppingService.setOrderDetailRepository(orderDetailRepository);
        shoppingService.setOrderItemRepository(orderItemRepository);

        assertThrows(DataNotFoundException.class, () -> shoppingService.checkout(request));
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง order_id = 1 พบข้อมูล")
    void getOrderSummary() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setUserId(20001);
        orderDetail.setPaymentId(30001);
        orderDetail.setInvoiceNumber(new Random().nextInt(10));
        orderDetail.setTotalPrice(5000);
        orderDetail.setOrderDate(new Date());
        orderDetail.setPaidDate(null);
        orderDetail.setPaymentExpireDate(null);
        when(orderDetailRepository.findById(1)).thenReturn(Optional.of(orderDetail));

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setOrderDetailRepository(orderDetailRepository);
        OrderDetail result = shoppingService.getOrderSummary(1);

        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("ทำการสั่งซื้อโดยส่ง order_id = 1 ไม่พบข้อมูล, throw = DataNotFoundException")
    void getOrderSummary02() {
        when(orderDetailRepository.findById(1)).thenReturn(Optional.empty());

        ShoppingService shoppingService = new ShoppingService();
        shoppingService.setOrderDetailRepository(orderDetailRepository);

        assertThrows(DataNotFoundException.class, () -> shoppingService.getOrderSummary(1));
    }

    @Test
    @DisplayName("คำนวณราคาสินค้าในตะกร้าถูกต้อง = 25000")
    void calculateTotalPrice() {
        List<Basket> baskets = new ArrayList<>();
        baskets.add(new Basket(20001, 10001, 15000, 1));
        baskets.add(new Basket(20001, 10002, 5000, 2));

        ShoppingService shoppingService = new ShoppingService();

        assertTrue(ReflectionTestUtils.invokeMethod(shoppingService, "calculatePrice", baskets).equals(25000));
    }

    @Test
    @DisplayName("คำนวณราคาสินค้าในตะกร้าถูกต้อง = 35000")
    void calculateTotalPrice02() {
        List<Basket> baskets = new ArrayList<>();
        baskets.add(new Basket(20001, 10001, 15000, 2));
        baskets.add(new Basket(20001, 10002, 5000, 1));

        ShoppingService shoppingService = new ShoppingService();

        assertTrue(ReflectionTestUtils.invokeMethod(shoppingService, "calculatePrice", baskets).equals(35000));
    }

}