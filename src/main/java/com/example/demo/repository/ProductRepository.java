package com.example.demo.repository;

import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    @Query("SELECT p FROM products p WHERE p.active=true AND p.stockQuantity>0 AND LOWER(p.name) LIKE(CONCAT('%', :keyword,'%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);
}
