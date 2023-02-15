package com.gsv.codeflix.admin.catalog.infrastructure.category.presenters;

import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.GetCategoryByIdOutput;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.list.GetCategoryListOutput;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CategoryResponse;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final GetCategoryByIdOutput output) {
        return new CategoryResponse(
                output.id(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final GetCategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}