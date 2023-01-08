package com.gsv.codeflix.admin.catalog.application.category.retrieve.get;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultGetCategoryByIdUseCaseTest {

    @InjectMocks
    DefaultGetCategoryByIdUseCase getCategoryById;

    @Mock
    CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_whenExecute_thenReturnsACategory() {
        final var category = Category.newCategory("NAME", "DESC", true);
        when(categoryGateway.findById(any())).thenReturn(Optional.of(category));

        final var actual = getCategoryById.execute(category.getId().getValue());

        Assertions.assertEquals(actual.id(), category.getId().getValue());
    }

    @Test
    public void givenAnInvalidId_whenExecute_ThenThrows() {
        final var category = Category.newCategory("NAME", "DESC", true);
        when(categoryGateway.findById(any())).thenReturn(Optional.ofNullable(null));

        final var exp = Assertions.assertThrows(
                DomainException.class,
                () -> getCategoryById.execute(category.getId().getValue())
        );

        Assertions.assertEquals(
                exp.getErrors().get(0).message(),
                "Category with id %s not found".formatted(category.getId().getValue())
        );
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = CategoryID.from("123");

        when(categoryGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> getCategoryById.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}