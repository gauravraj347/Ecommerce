package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.NotBlank;
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
 * Validation rules:
 *   @NotBlank -> the field must be present AND not empty/whitespace.
 * These are checked when a controller parameter is marked @Valid.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    // categoryId is set by the database on create, so we don't validate it as input.
    private Integer categoryId;

    @NotBlank(message = "categoryTitle must not be blank")
    private String categoryTitle;

    // imageUrl is optional -> no validation.
    private String imageUrl;
}
