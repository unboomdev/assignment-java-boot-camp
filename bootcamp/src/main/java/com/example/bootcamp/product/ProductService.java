package com.example.bootcamp.product;

import com.example.bootcamp.product.entities.Product;
import com.example.bootcamp.product.entities.ProductImage;
import com.example.bootcamp.product.exception.ProductNotFoundException;
import com.example.bootcamp.product.exception.SearchNotFoundException;
import com.example.bootcamp.product.model.ProductResponse;
import com.example.bootcamp.product.model.ProductSearchResponse;
import com.example.bootcamp.product.model.ProductsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setProductImageRepository(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    public ProductSearchResponse searchProduct(String query) {
        List<Product> results = productRepository.findByNameContainingIgnoreCase(query);
        if (!results.isEmpty()) {
            List<ProductsItem> products = new ArrayList<>();
            for (Product product: results) {
                Optional<ProductImage> imageResult = productImageRepository.findFirstByProductId(product.getId());
                String imageUrl = "";
                if (imageResult.isPresent()) {
                    imageUrl = imageResult.get().getUrl();
                }
                products.add(new ProductsItem(product.getId(), product.getName(), product.getPrice(), product.getDiscount(), imageUrl));
            }
            return new ProductSearchResponse(products, products.size());
        }
        throw new SearchNotFoundException("Search not found");
    }

    public ProductResponse getProductById(int id) {
        List<ProductImage> imageResult = productImageRepository.findByProductId(id);
        List<String> images = new ArrayList<>();
        for (ProductImage productImage : imageResult) {
            images.add(productImage.getUrl());
        }
        Optional<Product> productResult = productRepository.findById(id);
        if (productResult.isPresent()) {
            Product product = productResult.get();
            return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDiscount(), product.getDescription(), images);
        }
        throw new ProductNotFoundException("Product not found");
    }

}
