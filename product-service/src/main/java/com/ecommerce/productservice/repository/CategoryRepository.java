package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access layer for Category.
 *
 * By extending JpaRepository<Category, Integer> we get a full set of database
 * methods for FREE, with no implementation needed. Spring Data JPA generates
 * the code at runtime. The two type parameters mean:
 *   - Category -> the entity this repository manages
 *   - Integer  -> the type of its @Id (categoryId)
 *
 * Methods we get automatically include:
 *   save(entity)        -> INSERT or UPDATE
 *   findById(id)        -> SELECT by primary key (returns Optional)
 *   findAll()           -> SELECT all rows
 *   deleteById(id)      -> DELETE by primary key
 *   existsById(id), count(), ... and more
 *
 * @Repository is optional here (Spring Data detects the interface anyway),
 * but we add it for clarity and consistent exception translation.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
