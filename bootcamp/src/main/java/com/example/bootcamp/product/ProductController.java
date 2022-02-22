package com.example.bootcamp.product;

import com.example.bootcamp.product.model.ProductResponse;
import com.example.bootcamp.product.model.ProductSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/search")
    public ProductSearchResponse searchProduct(@RequestParam String q) {
        return productService.searchProduct(q);
    }

    @GetMapping("/product/{id}")
    public ProductResponse getProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

}
