package com.gsv.codeflix.admin.catalog.infrastructure.category.presenters;

import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.GetCategoryByIdOutput;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CategoryApiOutput;

public interface CategoryApiPresenter {

    static CategoryApiOutput present(final GetCategoryByIdOutput output) {
        return new CategoryApiOutput(
                output.id(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}