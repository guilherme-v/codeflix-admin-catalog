package com.gsv.codeflix.admin.catalog.infrastructure.api.controllers;

import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryInput;
import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.delete.DeleteCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.gsv.codeflix.admin.catalog.application.category.update.UpdateCategoryInput;
import com.gsv.codeflix.admin.catalog.application.category.update.UpdateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;
import com.gsv.codeflix.admin.catalog.infrastructure.api.CategoryAPI;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CreateCategoryApiInput;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CategoryApiOutput;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.UpdateCategoryRequest;
import com.gsv.codeflix.admin.catalog.infrastructure.category.presenters.CategoryApiPresenter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Tag(name = "Categories")
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;

    public CategoryController(
            CreateCategoryUseCase createCategoryUseCase,
            GetCategoryByIdUseCase getCategoryByIdUseCase,
            UpdateCategoryUseCase updateCategoryUseCase,
            DeleteCategoryUseCase deleteCategoryUseCase
    ) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.getCategoryByIdUseCase = getCategoryByIdUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
        final var cmdInput = CreateCategoryInput.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        return createCategoryUseCase.execute(cmdInput)
                .fold(
                        notification -> ResponseEntity.unprocessableEntity().body(notification),
                        output -> ResponseEntity.created(URI.create("/categories/" + output.id().getValue())).body(output)
                );
    }


    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, int sort, int dir) {
        return null;
    }

    @Override
    public CategoryApiOutput getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest request) {
        final var cmdInput = UpdateCategoryInput.with(
                id,
                request.name(),
                request.description(),
                request.active()
        );

        return updateCategoryUseCase.execute(cmdInput)
                .fold(
                        notification -> ResponseEntity.unprocessableEntity().body(notification),
                        ResponseEntity::ok
                );
    }

    @Override
    public void deleteById(final String id) {
        this.deleteCategoryUseCase.execute(id);
    }
}
