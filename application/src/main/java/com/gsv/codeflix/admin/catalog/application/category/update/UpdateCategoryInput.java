package com.gsv.codeflix.admin.catalog.application.category.update;


public record UpdateCategoryInput(
        String id,
        String name,
        String description,
        boolean isActive
) {
    public static UpdateCategoryInput with(
            final String id,
            final String name,
            final String description,
            final boolean isActive
    ) {
        return new UpdateCategoryInput(id, name, description, isActive);
    }
}
