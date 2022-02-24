package com.example.bootcamp.product;

import com.example.bootcamp.common.ExceptionResponse;
import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.model.ProductResponse;
import com.example.bootcamp.product.model.ProductSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("พบข้อมูลการค้นหา")
    void case01() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Adidas NMD R1 PK", 15000, 40, "ใส่สบายมาก"));
        products.add(new Product(2, "Adidas NMD Color", 13000, 35, "ใส่สบาย"));
        when(productRepository.findByNameContainingIgnoreCase("NMD")).thenReturn(products);

        ProductSearchResponse result = testRestTemplate.getForObject("/product/search?q=NMD", ProductSearchResponse.class);

        assertTrue(result.getTotal() > 0);
    }

    @Test
    @DisplayName("ไม่พบข้อมูลค้นหา msg = Search not found")
    void case02() {
        when(productRepository.findByNameContainingIgnoreCase("Nike")).thenReturn(new ArrayList<>());

        ExceptionResponse result = testRestTemplate.getForObject("/product/search?q=Nike", ExceptionResponse.class);

        assertEquals("Search not found", result.getMessage());
    }

    @Test
    @DisplayName("พบข้อมูลของสินค้าที่เลือก")
    void case03() {
        Product product = new Product(2, "Adidas NMD Color", 13000, 35, "ใส่สบาย");
        when(productRepository.findById(2)).thenReturn(Optional.of(product));

        ProductResponse result = testRestTemplate.getForObject("/product/2", ProductResponse.class);

        assertNotNull(result);
    }

    @Test
    @DisplayName("ไม่พบข้อมูลของสินค้าที่เลือก msg = Product not found")
    void case04() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        ExceptionResponse result = testRestTemplate.getForObject("/product/999", ExceptionResponse.class);

        assertEquals("Product not found", result.getMessage());
    }

    @Test
    @DisplayName("ส่ง product_id เป็น string แล้วจะได้ status_code = 400")
    void case05() {
        ResponseEntity response = testRestTemplate.getForEntity("/product/text", Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}