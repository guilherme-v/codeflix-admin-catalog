package com.gsv.codeflix.admin.catalog.application.category.create;

public record CreateCategoryInput(
        String name,
        String description,
        boolean isActive
) {

    public static CreateCategoryInput with(String name, String description, boolean active) {
        return new CreateCategoryInput(name, description, active);
    }
}
