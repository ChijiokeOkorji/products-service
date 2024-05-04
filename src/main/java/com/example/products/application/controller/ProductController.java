package com.example.products.application.controller;

import com.example.products.application.dto.Product;
import com.example.products.domain.service.ProductService;
import com.example.products.application.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<Product> createEntity(@Valid @RequestBody ProductRequest productRequest) {
        Product response = productService.createProduct(productRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<List<Product>> getAllEntities() {
        List<Product> response = productService.getAllProducts();

        if (ObjectUtils.isEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<Product> getEntityById(@PathVariable String productId) {
        Product response = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<Product> updateEntity(@PathVariable String productId, @Valid @RequestBody ProductRequest productRequest) {
        Product response = productService.updateProduct(productId, productRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteEntity(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
