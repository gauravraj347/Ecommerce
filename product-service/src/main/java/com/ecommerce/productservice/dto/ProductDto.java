package com.ecommerce.productservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    // INPUT: the id of the category to attach to this product (optional).
    private Integer categoryId;

    // OUTPUT: the attached category's details. Omitted from JSON when null.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryDto category;
}
