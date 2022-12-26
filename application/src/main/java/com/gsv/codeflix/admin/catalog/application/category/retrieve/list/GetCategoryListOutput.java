package com.gsv.codeflix.admin.catalog.application.category.retrieve.list;

import com.gsv.codeflix.admin.catalog.domain.category.Category;

import java.time.Instant;

public record GetCategoryListOutput(
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static GetCategoryListOutput from(Category category) {
        return new GetCategoryListOutput(
                category.getName(),
                category.getDescription(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getDeletedAt()
        );
    }
}
