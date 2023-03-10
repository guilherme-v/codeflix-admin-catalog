package com.gsv.codeflix.admin.catalog.application.category.update;

import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.exceptions.NotFoundException;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryInput param) {
        // FlatMap Note:
        // - if left, flatMap will move it forward through the original stream
        // - If right, it applies F (that creates another Either), and merges it backs to the original stream
        final var id = CategoryID.from(param.id());
        return Try(() -> categoryGateway.findById(id).orElseThrow(notFound(param.id())))
                .toEither()
                .flatMap(category -> Try(() -> category.update(param.name(), param.description(), param.isActive())).toEither())
                .flatMap(category -> Try(() -> categoryGateway.update(category)).toEither())
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private static Supplier<DomainException> notFound(final String id) {
        final var error = new Error("Category with id %s not found".formatted(id));
        return () -> NotFoundException.with(error);
    }
}
