package com.gsv.codeflix.admin.catalog.application.category;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.ThrowsValidationHandler;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public CreateCategoryOutput execute(CreateCategoryInput param) {

        final var aCategory = Category.newCategory(param.name(), param.description(), param.isActive());
        aCategory.validate(new ThrowsValidationHandler());

        categoryGateway.create(aCategory);

        return CreateCategoryOutput.with(aCategory);
    }
}
