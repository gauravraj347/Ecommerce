package com.ecommerce.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the product-service application.
 *
 * @SpringBootApplication turns this class into the starting point of a Spring Boot app:
 *   - it enables auto-configuration (Spring wires up sensible defaults),
 *   - it tells Spring to scan this package (and sub-packages) for components.
 */
@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
