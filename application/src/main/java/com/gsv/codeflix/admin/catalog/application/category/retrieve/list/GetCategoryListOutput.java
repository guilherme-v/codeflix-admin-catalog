package com.gsv.codeflix.admin.catalog.application.category.retrieve.list;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;

import java.time.Instant;

public record GetCategoryListOutput(
        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {
    public static GetCategoryListOutput from(Category aCategory) {
        return new GetCategoryListOutput(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.getDescription(),
                aCategory.getIsActive(),
                aCategory.getCreatedAt(),
                aCategory.getDeletedAt()
        );
    }
}