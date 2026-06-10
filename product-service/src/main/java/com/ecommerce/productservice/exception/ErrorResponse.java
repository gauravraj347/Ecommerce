package com.ecommerce.productservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

/**
 * The consistent JSON shape every error response uses.
 *
 * Using a record = an immutable data carrier with no boilerplate.
 *
 * @JsonInclude(NON_NULL) -> fields that are null are omitted from the JSON,
 * so validationErrors only appears when there actually are field errors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
    /** Convenience constructor for errors without per-field validation details. */
    public ErrorResponse(Instant timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, message, path, null);
    }
}
