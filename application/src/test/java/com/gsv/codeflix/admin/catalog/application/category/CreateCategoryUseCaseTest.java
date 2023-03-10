package com.gsv.codeflix.admin.catalog.application.category;

import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryInput;
import com.gsv.codeflix.admin.catalog.application.category.create.DefaultCreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCreateCategoryInput_whenCallExecute_thenReturnsAValidId() {
        final var expectedName = "MOVIE_XYZ";
        final var expectedDescription = "Description of the movie";
        final var expectedIsActive = true;
        final var input = CreateCategoryInput.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var output = useCase.execute(input);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.get().id());
        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedIsActive, aCategory.getIsActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAnInvalidCreateCategoryInput_whenCallExecute_thenThrows() {
        final var input = CreateCategoryInput.with("", "desc", true);
        final var notification = useCase.execute(input).getLeft();
        Assertions.assertEquals("'name' should not be empty", notification.getErrors().get(0).message());

        final var input2 = CreateCategoryInput.with(null, "desc", true);
        final var notification2 = useCase.execute(input2).getLeft();
        Assertions.assertEquals("'name' should not be null", notification2.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }
}