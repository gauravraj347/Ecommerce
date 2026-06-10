package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for categories.
 *
 *  @Service               -> marks this as a Spring-managed service bean.
 *  @RequiredArgsConstructor (Lombok) -> generates a constructor for all `final`
 *                            fields. Spring uses that constructor to inject the
 *                            CategoryRepository. This is "constructor injection",
 *                            the recommended approach (no @Autowired needed).
 *  @Slf4j                 -> gives us a `log` object for logging.
 *  @Transactional(readOnly = true) at class level -> read methods run in a
 *                            read-only transaction by default; write methods
 *                            below override it with a normal @Transactional so
 *                            their multiple DB operations are atomic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Create and persist a new category.
     * Note: we ignore any categoryId sent by the client — the database
     * generates it — so a create always inserts a brand-new row.
     */
    @Transactional
    public CategoryDto create(CategoryDto request) {
        log.info("Creating category with title '{}'", request.getCategoryTitle());

        Category category = Category.builder()
                .categoryTitle(request.getCategoryTitle())
                .imageUrl(request.getImageUrl())
                .parentCategory(resolveParent(request.getParentCategoryId(), null))
                .build();

        Category saved = categoryRepository.save(category);

        log.info("Created category with id {}", saved.getCategoryId());
        return toDto(saved);
    }

    /**
     * Return all categories.
     * findAll() runs "SELECT * FROM categories"; we map each entity to a DTO.
     */
    public List<CategoryDto> findAll() {
        log.info("Fetching all categories");
        return categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Return a single category by id.
     * findById() returns an Optional; if it's empty we throw a clear
     * "not found" exception instead of returning null.
     */
    public CategoryDto findById(Integer categoryId) {
        log.info("Fetching category with id {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + categoryId));
        return toDto(category);
    }

    /**
     * Update an existing category's fields.
     * We load the existing row first (so a missing id gives a clear 404 later),
     * then copy the editable fields and save. We deliberately do NOT change the id.
     */
    @Transactional
    public CategoryDto update(Integer categoryId, CategoryDto request) {
        log.info("Updating category with id {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + categoryId));

        category.setCategoryTitle(request.getCategoryTitle());
        category.setImageUrl(request.getImageUrl());
        category.setParentCategory(resolveParent(request.getParentCategoryId(), categoryId));

        // saveAndFlush (not save) forces the UPDATE now, so @LastModifiedDate
        // fires and the returned entity carries the fresh updatedAt.
        Category saved = categoryRepository.saveAndFlush(category);
        return toDto(saved);
    }

    /**
     * Delete a category by id.
     * We check existence first so a missing id gives a clear "not found"
     * (the clone silently did nothing for unknown ids).
     */
    @Transactional
    public void deleteById(Integer categoryId) {
        log.info("Deleting category with id {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Look up the parent category to attach.
     *  - null id      -> no parent (top-level category)
     *  - own id        -> illegal (a category cannot be its own parent) -> 400
     *  - unknown id    -> 404
     */
    private Category resolveParent(Integer parentCategoryId, Integer selfId) {
        if (parentCategoryId == null) {
            return null;
        }
        if (parentCategoryId.equals(selfId)) {
            throw new IllegalArgumentException(
                    "A category cannot be its own parent (id: " + selfId + ")");
        }
        return categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Parent category not found with id: " + parentCategoryId));
    }

    /** Convert an entity into the DTO we expose through the API. */
    private CategoryDto toDto(Category category) {
        Category parent = category.getParentCategory();
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                .parentCategoryId(parent != null ? parent.getCategoryId() : null)
                .parentCategory(parent != null ? toShallowDto(parent) : null)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    /** A parent's details without ITS own parent — keeps the JSON one level deep. */
    private CategoryDto toShallowDto(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
