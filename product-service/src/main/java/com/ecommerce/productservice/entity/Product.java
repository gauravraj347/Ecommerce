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

import java.math.BigDecimal;

/**
 * A sellable product (e.g. a specific laptop).
 *
 * Same annotations as Category. Notable column choices:
 *   - sku is unique (no two products share a stock-keeping unit).
 *   - priceUnit uses BigDecimal (exact decimal) instead of the clone's Double,
 *     because Double cannot represent money exactly. precision/scale = 12,2
 *     means up to 10 digits before the decimal point and 2 after.
 *
 * A product belongs to one Category (@ManyToOne). We use LAZY fetch so the
 * category is loaded only when actually accessed (we access it inside the
 * service's transaction when mapping to a DTO). This avoids the clone's
 * heavier EAGER + bidirectional + cascade=ALL setup.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_title", nullable = false)
    private String productTitle;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "price_unit", precision = 12, scale = 2)
    private BigDecimal priceUnit;

    @Column(name = "quantity")
    private Integer quantity;

    /** The category this product belongs to (foreign key column: category_id). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
