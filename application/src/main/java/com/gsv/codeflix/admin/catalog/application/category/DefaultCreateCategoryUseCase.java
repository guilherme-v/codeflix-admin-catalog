package com.gsv.codeflix.admin.catalog.application.category;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryInput param) {
        final var aCategory = Category.newCategory(param.name(), param.description(), param.isActive());
        final var notification = Notification.create();

        aCategory.validate(notification);
        if (notification.hasErrors()) {
            return Left(notification);
        }

        return Try(() -> categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::with);
    }
}
