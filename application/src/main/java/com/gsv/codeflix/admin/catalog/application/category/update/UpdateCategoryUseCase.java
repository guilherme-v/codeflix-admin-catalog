package com.gsv.codeflix.admin.catalog.application.category.update;

import com.gsv.codeflix.admin.catalog.application.UseCase;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public class UpdateCategoryUseCase extends UseCase<UpdateCategoryInput, Either<Notification, UpdateCategoryOutput>> {

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryInput param) {
        return null;
    }
}
