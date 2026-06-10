package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for Product. Same idea as CategoryRepository:
 * JpaRepository<Product, Integer> gives us save/findById/findAll/deleteById/etc.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
