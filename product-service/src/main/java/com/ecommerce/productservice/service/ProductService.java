package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for products. Same structure as CategoryService:
 * constructor injection, read-only transactions by default, write methods
 * marked @Transactional, and entity<->DTO mapping kept private.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductDto create(ProductDto request) {
        log.info("Creating product with title '{}'", request.getProductTitle());

        Product product = Product.builder()
                .productTitle(request.getProductTitle())
                .imageUrl(request.getImageUrl())
                .sku(request.getSku())
                .priceUnit(request.getPriceUnit())
                .quantity(request.getQuantity())
                .build();

        Product saved = productRepository.save(product);
        log.info("Created product with id {}", saved.getProductId());
        return toDto(saved);
    }

    public List<ProductDto> findAll() {
        log.info("Fetching all products");
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ProductDto findById(Integer productId) {
        log.info("Fetching product with id {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + productId));
        return toDto(product);
    }

    @Transactional
    public ProductDto update(Integer productId, ProductDto request) {
        log.info("Updating product with id {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + productId));

        product.setProductTitle(request.getProductTitle());
        product.setImageUrl(request.getImageUrl());
        product.setSku(request.getSku());
        product.setPriceUnit(request.getPriceUnit());
        product.setQuantity(request.getQuantity());

        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    @Transactional
    public void deleteById(Integer productId) {
        log.info("Deleting product with id {}", productId);
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    /** Convert an entity into the DTO we expose through the API. */
    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productTitle(product.getProductTitle())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .priceUnit(product.getPriceUnit())
                .quantity(product.getQuantity())
                .build();
    }
}
