package com.gsv.codeflix.admin.catalog.application.category.update;

import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;

public record UpdateCategoryInput(
        CategoryID id,
        String name,
        String description,
        boolean isActive
) {
    public static UpdateCategoryInput with(
            final CategoryID id, final String name, final String description, final boolean isActive
    ) {
        return new UpdateCategoryInput(id, name, description, isActive);
    }
}
