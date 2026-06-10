package com.ecommerce.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A product category (e.g. "Electronics", "Books").
 *
 * Annotations explained:
 *   @Entity            -> this class is a JPA entity = maps to a database table.
 *   @Table(name=...)   -> the table is called "categories".
 *   @Id                -> categoryId is the primary key.
 *   @GeneratedValue(IDENTITY) -> PostgreSQL auto-increments the id for us.
 *   @Column(name=...)  -> maps a field to a specific column name.
 *
 * Lombok annotations (generate boilerplate at compile time):
 *   @Getter @Setter    -> getters/setters for every field.
 *   @NoArgsConstructor -> JPA requires a no-argument constructor.
 *   @AllArgsConstructor + @Builder -> convenient ways to create objects.
 *
 * NOTE: We are intentionally keeping this flat for now. The cloned version
 * also had a parent/sub-category tree and a list of products; we will add
 * those later (Step 14) only if we decide we need them.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_title", nullable = false)
    private String categoryTitle;

    @Column(name = "image_url")
    private String imageUrl;
}
