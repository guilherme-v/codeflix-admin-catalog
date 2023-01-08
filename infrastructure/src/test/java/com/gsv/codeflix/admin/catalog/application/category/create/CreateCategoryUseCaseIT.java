package com.gsv.codeflix.admin.catalog.application.category.create;

import com.gsv.codeflix.admin.catalog.IntegrationTest;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import com.gsv.codeflix.admin.catalog.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    CreateCategoryUseCase useCase;

    @Autowired
    CategoryRepository categoryRepository;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCreateCategoryInput_whenCallExecute_thenReturnsAValidId() {
        final var expectedName = "MOVIE_XYZ";
        final var expectedDescription = "Description of the movie";
        final var expectedIsActive = true;
        final var input = CreateCategoryInput.with(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryRepository.count());
        final var output = useCase.execute(input).get();

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());

        Assertions.assertEquals(1, categoryRepository.count());

        final var category = categoryRepository.findById(output.id().getValue()).get();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        Assertions.assertEquals(0, categoryRepository.count());

        final var aCommand =
                CreateCategoryInput.with(expectedName, expectedDescription, expectedIsActive);

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());

        Assertions.assertEquals(0, categoryRepository.count());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand =
                CreateCategoryInput.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.doThrow(new IllegalStateException(expectedErrorMessage))
                .when(categoryGateway).create(any());

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());
    }
}
