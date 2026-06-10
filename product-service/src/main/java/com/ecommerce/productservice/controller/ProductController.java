package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API for products. Mirrors CategoryController:
 * POST(201) / GET all / GET by id / PUT / DELETE(204).
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto request) {
        log.info("POST /api/products");
        ProductDto created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        log.info("GET /api/products");
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> findById(@PathVariable Integer productId) {
        log.info("GET /api/products/{}", productId);
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> update(@PathVariable Integer productId,
                                             @Valid @RequestBody ProductDto request) {
        log.info("PUT /api/products/{}", productId);
        return ResponseEntity.ok(productService.update(productId, request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Integer productId) {
        log.info("DELETE /api/products/{}", productId);
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
