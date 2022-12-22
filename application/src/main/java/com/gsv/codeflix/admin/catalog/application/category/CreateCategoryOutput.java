package com.gsv.codeflix.admin.catalog.application.category;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {

    public static CreateCategoryOutput with(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
