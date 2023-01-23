package com.gsv.codeflix.admin.catalog.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsv.codeflix.admin.catalog.ControllerTests;
import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryOutput;
import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.Notification;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CreateCategoryApiInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTests(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryID() throws Exception {
        final var expectedName = "movies";
        final var expectedDesc = "The most loved category";
        final var expectedIsActive = true;

        final var input = new CreateCategoryApiInput(expectedName, expectedDesc, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from(CategoryID.from("123"))));

        final var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/categories/123")
                );

        verify(createCategoryUseCase, times(1)).execute(argThat(arg ->
                Objects.equals(arg.name(), expectedName) &&
                        Objects.equals(arg.description(), expectedDesc) &&
                        Objects.equals(arg.isActive(), expectedIsActive)
        ));
    }

    @Test
    public void givenAnInvalidCommand_whenCallsCreateCategory_shouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDesc = "The most loved category";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var input = new CreateCategoryApiInput(expectedName, expectedDesc, expectedIsActive);
        final var notif = Notification.create(new Error(expectedMessage));
        when(createCategoryUseCase.execute(any())).thenReturn(Left(notif));

        final var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(arg ->
                Objects.equals(arg.name(), expectedName) &&
                        Objects.equals(arg.description(), expectedDesc) &&
                        Objects.equals(arg.isActive(), expectedIsActive)
        ));
    }

    @Test
    public void givenAnUnexpectedProblem_whenCallsCreateCategory_shouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDesc = "The most loved category";
        final var expectedIsActive = true;
        final var expectedMessage = "unexpected error";

        final var input = new CreateCategoryApiInput(expectedName, expectedDesc, expectedIsActive);
        final var error = new Error(expectedMessage);
        when(createCategoryUseCase.execute(any())).thenThrow(DomainException.with(error));

        final var request = MockMvcRequestBuilders
                .post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(arg ->
                Objects.equals(arg.name(), expectedName) &&
                        Objects.equals(arg.description(), expectedDesc) &&
                        Objects.equals(arg.isActive(), expectedIsActive)
        ));
    }
}
