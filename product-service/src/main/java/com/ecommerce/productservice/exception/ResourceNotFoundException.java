package com.ecommerce.productservice.exception;

/**
 * Thrown when a requested resource (e.g. a category) does not exist.
 *
 * Extends RuntimeException so we don't have to declare it everywhere.
 * For now, throwing this returns an ugly HTTP 500. In Step 11 we add a
 * global exception handler that turns it into a clean 404 Not Found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
