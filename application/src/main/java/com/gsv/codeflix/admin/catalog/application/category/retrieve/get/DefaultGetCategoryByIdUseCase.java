package com.gsv.codeflix.admin.catalog.application.category.retrieve.get;

import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;

import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public GetCategoryByIdOutput execute(final String id) {
        final var categoryID = CategoryID.from(id);

        return this.categoryGateway.findById(categoryID)
                .map(GetCategoryByIdOutput::from)
                .orElseThrow(notFound(categoryID));
    }

    private static Supplier<DomainException> notFound(CategoryID param) {
        final var error = new Error("Category with id %s not found".formatted(param.getValue()));
        return () -> DomainException.with(error);
    }
}
