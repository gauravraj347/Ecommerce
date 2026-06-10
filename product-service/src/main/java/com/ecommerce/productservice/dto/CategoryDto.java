package com.ecommerce.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Category.
 *
 * This is the shape of the JSON our REST API accepts (in request bodies) and
 * returns (in responses). It is deliberately SEPARATE from the Category entity
 * so the API contract and the database schema can evolve independently.
 *
 * Validation rules (e.g. @NotBlank) will be added here in Step 10.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Integer categoryId;
    private String categoryTitle;
    private String imageUrl;
}
