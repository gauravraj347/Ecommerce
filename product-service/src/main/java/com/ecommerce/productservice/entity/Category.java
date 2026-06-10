package com.ecommerce.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
 * A category may have an optional PARENT category (self-reference), so
 * categories form a tree. Top-level categories have a null parent. We use a
 * single @ManyToOne (LAZY) parent link and avoid the clone's bidirectional
 * subCategories collection + cascade=ALL, which invited recursion and accidents.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_title", nullable = false)
    private String categoryTitle;

    @Column(name = "image_url")
    private String imageUrl;

    /** Optional parent (self-reference). FK column: parent_category_id. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;
}
