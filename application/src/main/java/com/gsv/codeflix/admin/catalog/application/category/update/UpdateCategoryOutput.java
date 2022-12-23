package com.gsv.codeflix.admin.catalog.application.category.update;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput with(Category category) {
        return new UpdateCategoryOutput(category.getId());
    }
}
