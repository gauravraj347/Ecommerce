package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Business logic for categories.
 *
 *  @Service               -> marks this as a Spring-managed service bean.
 *  @RequiredArgsConstructor (Lombok) -> generates a constructor for all `final`
 *                            fields. Spring uses that constructor to inject the
 *                            CategoryRepository. This is "constructor injection",
 *                            the recommended approach (no @Autowired needed).
 *  @Slf4j                 -> gives us a `log` object for logging.
 *
 * We start with just create(). More methods (findAll, findById, update, delete)
 * are added in Step 9 as we build each endpoint, one at a time.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Create and persist a new category.
     * Note: we ignore any categoryId sent by the client — the database
     * generates it — so a create always inserts a brand-new row.
     */
    public CategoryDto create(CategoryDto request) {
        log.info("Creating category with title '{}'", request.getCategoryTitle());

        Category category = Category.builder()
                .categoryTitle(request.getCategoryTitle())
                .imageUrl(request.getImageUrl())
                .build();

        Category saved = categoryRepository.save(category);

        log.info("Created category with id {}", saved.getCategoryId());
        return toDto(saved);
    }

    /** Convert an entity into the DTO we expose through the API. */
    private CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .imageUrl(category.getImageUrl())
                .build();
    }
}
