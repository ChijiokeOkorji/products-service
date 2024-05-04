package com.example.products.infrastructure.mapper;

import com.example.products.application.dto.Product;
import com.example.products.application.dto.ProductRequest;
import com.example.products.infrastructure.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public ProductEntity toEntityModel(ProductRequest productRequest) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productRequest.getProductName());
        productEntity.setCategories(productRequest.getCategories());
        return productEntity;
    }
    public Product fromEntityModel(ProductEntity productEntity){
        Product product = new Product();
        product.setId(productEntity.getId().toString());
        product.setProductName(productEntity.getProductName());
        product.setCategories(productEntity.getCategories());
        return product;
    }
    public ProductEntity toEntityUpdateModel(ProductEntity productEntity, ProductRequest productRequest){
        productEntity.setProductName(productRequest.getProductName());
        productEntity.setCategories(productRequest.getCategories());
        return productEntity;
    }
}
