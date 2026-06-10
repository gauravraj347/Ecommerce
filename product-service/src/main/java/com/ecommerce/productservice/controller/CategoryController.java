package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST API for categories.
 *
 *  @RestController          -> this class handles HTTP requests and returns
 *                              JSON (it combines @Controller + @ResponseBody).
 *  @RequestMapping("/api/categories") -> every endpoint here starts with that path.
 *  @RequiredArgsConstructor -> injects CategoryService via the constructor.
 *
 * We add ONE endpoint at a time. This is endpoint #1: create (POST).
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Create a new category.
     *
     *  @PostMapping   -> handles HTTP POST to /api/categories
     *  @RequestBody   -> Spring converts the incoming JSON into a CategoryDto
     *  ResponseEntity -> lets us control the HTTP status code + body
     *
     * Returns 201 CREATED (more correct than the clone's 200 OK for a create).
     */
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto request) {
        log.info("POST /api/categories");
        CategoryDto created = categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Endpoint #2: list all categories.
     *
     *  @GetMapping -> handles HTTP GET to /api/categories
     * Returns 200 OK with a JSON array of categories (empty array if none).
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        log.info("GET /api/categories");
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * Endpoint #3: get one category by id.
     *
     *  @GetMapping("/{categoryId}") -> matches e.g. GET /api/categories/1
     *  @PathVariable               -> binds the {categoryId} URL part to the
     *                                 method parameter (as an Integer directly,
     *                                 unlike the clone which used String + parseInt).
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Integer categoryId) {
        log.info("GET /api/categories/{}", categoryId);
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }
}
