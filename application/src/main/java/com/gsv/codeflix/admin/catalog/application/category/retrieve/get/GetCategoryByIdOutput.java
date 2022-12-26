package com.gsv.codeflix.admin.catalog.application.category.retrieve.get;

import com.gsv.codeflix.admin.catalog.domain.category.Category;

import java.time.Instant;

public record GetCategoryByIdOutput(
        String id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static GetCategoryByIdOutput from(Category category) {
        return new GetCategoryByIdOutput(
                category.getId().getValue(),
                category.getName(),
                category.getDescription(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }
}
