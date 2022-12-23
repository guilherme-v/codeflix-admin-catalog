package com.gsv.codeflix.admin.catalog.domain.category;

import com.gsv.codeflix.admin.catalog.domain.AggregateRoot;
import com.gsv.codeflix.admin.catalog.domain.validation.ValidationHandler;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.ThrowsValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {
    private String name;
    private String description;
    private boolean isActive;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(CategoryID id, String name, String description, boolean isActive, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(String name, String desc, boolean isActive) {
        final var id = CategoryID.unique();
        final var timestamp = Instant.now();
        return new Category(id, name, desc, isActive, timestamp, timestamp, null);
    }

    public CategoryID getId() {
        return id;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Category deactivate() {
        final var instant = Instant.now();
        if (getDeletedAt() == null) {
            this.deletedAt = instant;
        }

        this.isActive = false;
        this.updatedAt = instant;

        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.isActive = true;
        this.updatedAt = Instant.now();

        return this;
    }

    public Category update(String name, String desc, boolean isActive) {
        this.name = name;
        this.description = desc;

        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.updatedAt = Instant.now();

        validate(new ThrowsValidationHandler());

        return this;
    }


    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}