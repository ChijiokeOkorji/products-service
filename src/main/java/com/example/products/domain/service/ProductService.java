package com.example.products.domain.service;

import com.example.products.infrastructure.mapper.ProductDtoMapper;
import com.example.products.infrastructure.repository.ProductRepository;
import com.example.products.application.dto.Product;
import com.example.products.application.dto.ProductRequest;
import com.example.products.infrastructure.entity.ProductEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    public Product createProduct(ProductRequest productRequest) {
        ProductEntity productEntity = productDtoMapper.toEntityModel(productRequest);
        ProductEntity productEntityResponse = productRepository.save(productEntity);
        return productDtoMapper.fromEntityModel(productEntityResponse);
    }

    public List<Product> getAllProducts() {
        List<ProductEntity> productEntityResponse = productRepository.findAll();
        return productEntityResponse.stream()
                .map(productDtoMapper::fromEntityModel)
                .toList();
    }

    public Product getProductById(String productId) {
        ProductEntity productEntityResponse = productRepository.findById(UUID.fromString(productId)).orElse(null);

        if (Objects.nonNull(productEntityResponse)) {
            return productDtoMapper.fromEntityModel(productEntityResponse);
        } else {
            return null;
        }
    }

    public Product updateProduct(String productId, ProductRequest productRequest) {
        ProductEntity existingProduct = productRepository.findById(UUID.fromString(productId))
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        ProductEntity productEntity = productDtoMapper.toEntityUpdateModel(existingProduct, productRequest);
        ProductEntity productEntityResponse = productRepository.save(productEntity);
        return productDtoMapper.fromEntityModel(productEntityResponse);
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(UUID.fromString(productId));
    }
}
