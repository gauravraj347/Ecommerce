package com.ecommerce.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Reusable base class that adds automatic audit timestamps to any entity.
 *
 *  @MappedSuperclass -> the fields below are mapped into the SUBCLASS's table
 *                       (there is no "abstract_audit_entity" table).
 *  @EntityListeners(AuditingEntityListener.class) -> lets Spring Data fill in
 *                       the timestamps automatically on insert/update.
 *  @CreatedDate / @LastModifiedDate -> set on first save / on every update.
 *
 * Requires @EnableJpaAuditing somewhere in the app (we put it on the main class).
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;
}
