package com.gsv.codeflix.admin.catalog.application.category;

import com.gsv.codeflix.admin.catalog.application.UseCase;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public class CreateCategoryUseCase extends UseCase<CreateCategoryInput, Either<Notification, CreateCategoryOutput>> {

    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryInput param) {
        return null;
    }
}
