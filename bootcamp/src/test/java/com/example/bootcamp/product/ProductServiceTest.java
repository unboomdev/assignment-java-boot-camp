package com.example.bootcamp.product;

import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.entities.ProductImage;
import com.example.bootcamp.product.exception.ProductNotFoundException;
import com.example.bootcamp.product.exception.SearchNotFoundException;
import com.example.bootcamp.product.model.ProductResponse;
import com.example.bootcamp.product.model.ProductSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Test
    @DisplayName("ค้นหาด้วย keyword = NMD แล้วพบข้อมูลสินค้า 3 ชิ้น")
    void searchProduct() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(10001, "Adidas NMD R1 PK", 15000, 40, "ใส่สบายมาก"));
        products.add(new Product(10002, "Adidas NMD Color", 13000, 35, "ใส่สบาย"));
        products.add(new Product(10003, "NMD Sneakers Fashion", 500, 75, "ถูกเกิ๊น"));
        when(productRepository.findByNameContainingIgnoreCase("NMD")).thenReturn(products);

        ProductImage productImage = new ProductImage(1, "http://image.com/1", 10001);
        when(productImageRepository.findFirstByProductId(10001)).thenReturn(Optional.of(productImage));

        ProductService productService = new ProductService();
        productService.setProductRepository(productRepository);
        productService.setProductImageRepository(productImageRepository);
        ProductSearchResponse result = productService.searchProduct("NMD");

        assertEquals(3, result.getTotal());
        assertEquals(3, result.getProducts().size());
    }

    @Test
    @DisplayName("ค้นหาด้วย keyword = nike จะไม่พบข้อมูล + throw SearchNotFoundException")
    void searchProductNotFound() {
        when(productRepository.findByNameContainingIgnoreCase("nike")).thenReturn(new ArrayList<>());

        ProductService productService = new ProductService();
        productService.setProductRepository(productRepository);

        assertThrows(SearchNotFoundException.class, () -> productService.searchProduct("nike"));
    }

    @Test
    @DisplayName("ส่ง product_id = 2 แล้วจะต้องได้ข้อมูลมี product_id = 2")
    void getProductById() {
        Product product = new Product(2, "Adidas NMD Color", 13000, 35, "ใส่สบาย");
        when(productRepository.findById(2)).thenReturn(Optional.of(product));

        ProductService productService = new ProductService();
        productService.setProductRepository(productRepository);
        productService.setProductImageRepository(productImageRepository);
        ProductResponse result = productService.getProductById(2);

        assertEquals(2, result.getId());
    }

    @Test
    @DisplayName("ส่ง product_id = 999 จะไม่พบข้อมูล + throw ProductNotFoundException")
    void getProductByIdNotFound() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        ProductService productService = new ProductService();
        productService.setProductRepository(productRepository);
        productService.setProductImageRepository(productImageRepository);

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(999));
    }
}