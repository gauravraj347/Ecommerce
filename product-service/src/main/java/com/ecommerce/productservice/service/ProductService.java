package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.repository.CategoryRepository;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductDto create(ProductDto request) {
        log.info("Creating product with title '{}'", request.getProductTitle());

        Product product = Product.builder()
                .productTitle(request.getProductTitle())
                .imageUrl(request.getImageUrl())
                .sku(request.getSku())
                .priceUnit(request.getPriceUnit())
                .quantity(request.getQuantity())
                .category(resolveCategory(request.getCategoryId()))
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

    /**
     * Return one "page" of products.
     * findAll(Pageable) runs a SELECT with LIMIT/OFFSET (and ORDER BY if sorted)
     * plus a COUNT for totals. .map(...) converts Page<Product> to Page<ProductDto>.
     */
    public Page<ProductDto> findPage(Pageable pageable) {
        log.info("Fetching products page (page={}, size={}, sort={})",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return productRepository.findAll(pageable).map(this::toDto);
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
        product.setCategory(resolveCategory(request.getCategoryId()));

        // saveAndFlush (not save) forces the UPDATE now, so @LastModifiedDate
        // fires and the returned entity carries the fresh updatedAt.
        Product saved = productRepository.saveAndFlush(product);
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

    /**
     * Look up the category to attach.
     *  - null id  -> no category (allowed; a product can be uncategorized)
     *  - unknown id -> 404 via our global handler
     */
    private Category resolveCategory(Integer categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + categoryId));
    }

    /** Convert an entity into the DTO we expose through the API. */
    private ProductDto toDto(Product product) {
        Category category = product.getCategory();

        // Null-safe: only build the nested category when the product has one
        // (the clone always dereferenced category -> NullPointerException).
        CategoryDto categoryDto = null;
        Integer categoryId = null;
        if (category != null) {
            categoryId = category.getCategoryId();
            categoryDto = CategoryDto.builder()
                    .categoryId(category.getCategoryId())
                    .categoryTitle(category.getCategoryTitle())
                    .imageUrl(category.getImageUrl())
                    .build();
        }

        return ProductDto.builder()
                .productId(product.getProductId())
                .productTitle(product.getProductTitle())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .priceUnit(product.getPriceUnit())
                .quantity(product.getQuantity())
                .categoryId(categoryId)
                .category(categoryDto)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
