package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Product — the JSON shape the API accepts/returns.
 * Kept separate from the Product entity, same as CategoryDto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    // Set by the database on create, so not validated as input.
    private Integer productId;

    @NotBlank(message = "productTitle must not be blank")
    private String productTitle;

    private String imageUrl;
    private String sku;
    private BigDecimal priceUnit;
    private Integer quantity;
}
