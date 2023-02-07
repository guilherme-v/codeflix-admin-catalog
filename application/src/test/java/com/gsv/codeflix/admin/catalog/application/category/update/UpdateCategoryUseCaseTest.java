package com.gsv.codeflix.admin.catalog.application.category.update;

import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class UpdateCategoryUseCaseTest {

    @Test
    void givenAnExistingCategory_whenExecute_thenItShouldBeUpdated() {
        final var initialCategory = Category.newCategory("NAME", "DESC", true);
        final var categoryUpdated = initialCategory.clone().update("NAME2", "DESC2", false);
        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        when(categoryGateway.findById(any())).thenReturn(Optional.of(initialCategory));

        when(categoryGateway.update(any())).thenReturn(categoryUpdated);

        final var input = UpdateCategoryInput.with(initialCategory.getId().getValue(), "NAME2", "DESC2", false);
        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var actual = useCase.execute(input).get();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.id(), initialCategory.getId().getValue());

        Mockito.verify(categoryGateway, times(1)).findById(initialCategory.getId());
        Mockito.verify(categoryGateway, times(1)).update(argThat(category -> Objects.equals(category.getName(), input.name()) && Objects.equals(category.getId().getValue(), input.id()) && Objects.equals(category.getDescription(), input.description()) && Objects.equals(category.getIsActive(), input.isActive())));
    }

    @Test
    void givenAnCategoryThatDoesNotExist_whenExecute_thenItShouldNotify() {
        final var initialCategory = Category.newCategory("NAME", "DESC", true);
        final var categoryUpdated = initialCategory.clone().update("NAME2", "DESC2", false);
        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        when(categoryGateway.findById(any())).thenReturn(Optional.empty());

        final var input = UpdateCategoryInput.with(initialCategory.getId().getValue(), "NAME2", "DESC2", false);

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var notification = useCase.execute(input).getLeft();
        Assertions.assertNotNull(notification);
        Assertions.assertEquals(notification.getErrors().get(0).message(), "Category with id %s not found".formatted(initialCategory.getId().getValue()));

        Mockito.verify(categoryGateway, times(1)).findById(initialCategory.getId());
        Mockito.verify(categoryGateway, times(0));
    }

    @Test
    void givenAnInvalidCategory_whenExecute_thenItShouldNotify() {
        final var category = Category.newCategory("OK", "DESC", true);
        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        when(categoryGateway.findById(any())).thenReturn(Optional.of(category));

        final var input = UpdateCategoryInput.with(category.getId().getValue(), null, // invalid
                "DESC2", false);

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var notification = useCase.execute(input).getLeft();
        Assertions.assertNotNull(notification);
        Assertions.assertEquals(notification.getErrors().get(0).message(), "'name' should not be null");

        Mockito.verify(categoryGateway, times(1)).findById(category.getId());
        Mockito.verify(categoryGateway, times(0)).update(any());
    }

    @Test
    void givenUnexpectedIssues_withGatewayFindById_whenExecute_thenItShouldNotify() {
        final var initialCategory = Category.newCategory("NAME", "DESC", true);
        final var categoryGateway = Mockito.mock(CategoryGateway.class);

        when(categoryGateway.findById(any())).thenThrow(new IllegalStateException("bla"));

        final var input = UpdateCategoryInput.with(initialCategory.getId().getValue(), "NAME2", "DESC2", false);

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var notification = useCase.execute(input).getLeft();
        Assertions.assertNotNull(notification);

        Mockito.verify(categoryGateway, times(1)).findById(initialCategory.getId());
        Mockito.verify(categoryGateway, times(0)).update(any());
    }

    @Test
        void givenUnexpectedIssues_withGatewayUpdate_whenExecute_thenItShouldNotify() {
        final var category = Category.newCategory("OK", "DESC", true);
        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        final var input = UpdateCategoryInput.with(category.getId().getValue(), "NAME2", "DESC2", false);

        when(categoryGateway.findById(any())).thenReturn(Optional.of(category));
        when(categoryGateway.update(any())).thenThrow(new IllegalStateException("bla"));

        final var useCase = new DefaultUpdateCategoryUseCase(categoryGateway);
        final var notification = useCase.execute(input).getLeft();

        Assertions.assertNotNull(notification);
        Mockito.verify(categoryGateway, times(1)).findById(category.getId());
        Mockito.verify(categoryGateway, times(1)).update(any());
    }
}