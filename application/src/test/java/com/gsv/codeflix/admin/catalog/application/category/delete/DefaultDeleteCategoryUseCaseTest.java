package com.gsv.codeflix.admin.catalog.application.category.delete;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDeleteCategoryUseCaseTest {

    @InjectMocks
    DefaultDeleteCategoryUseCase defaultDeleteCategoryUseCase;

    @Mock
    CategoryGateway categoryGateway;

    @Test
    public void givenAValidId_WhenExecute_thenShouldBeOK() {
        final var category = Category.newCategory("NAME", "DESC", true);
        final var categoryID = category.getId();

        doNothing().when(categoryGateway).deleteById(any());

        defaultDeleteCategoryUseCase.execute(categoryID.getValue());

        verify(categoryGateway, times(1)).deleteById(categoryID);
    }

    @Test
    public void givenAnInvalidId_WhenExecute_thenShouldBeOK() {
        final var category = Category.newCategory("-", "DESC", true);
        final var categoryID = category.getId();

        doNothing().when(categoryGateway).deleteById(any());

        defaultDeleteCategoryUseCase.execute(categoryID.getValue());

        verify(categoryGateway, times(1)).deleteById(categoryID);
    }

    @Test
    public void giveUnexpectedErrorsOnGateway_whenExecute_thenShouldThrows() {
        final var category = Category.newCategory("-", "DESC", true);
        final var categoryID = category.getId();

        doThrow(new IllegalStateException("BLA")).when(categoryGateway).deleteById(any());

        Assertions.assertThrows(IllegalStateException.class, () -> defaultDeleteCategoryUseCase.execute(categoryID.getValue()));

        verify(categoryGateway, times(1)).deleteById(categoryID);
    }
}