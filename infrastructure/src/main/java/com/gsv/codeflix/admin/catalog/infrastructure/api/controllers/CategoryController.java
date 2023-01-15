package com.gsv.codeflix.admin.catalog.infrastructure.api.controllers;

import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryInput;
import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;
import com.gsv.codeflix.admin.catalog.infrastructure.api.CategoryAPI;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CreateCategoryApiInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
@Tag(name = "Categories")
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @Override
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
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
}
