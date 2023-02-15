package com.gsv.codeflix.admin.catalog.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsv.codeflix.admin.catalog.ControllerTests;
import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryOutput;
import com.gsv.codeflix.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.delete.DeleteCategoryUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.GetCategoryByIdOutput;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.list.GetCategoryListOutput;
import com.gsv.codeflix.admin.catalog.application.category.retrieve.list.GetCategoryListUseCase;
import com.gsv.codeflix.admin.catalog.application.category.update.UpdateCategoryOutput;
import com.gsv.codeflix.admin.catalog.application.category.update.UpdateCategoryUseCase;
import com.gsv.codeflix.admin.catalog.domain.category.Category;
import com.gsv.codeflix.admin.catalog.domain.category.CategoryID;
import com.gsv.codeflix.admin.catalog.domain.exceptions.DomainException;
import com.gsv.codeflix.admin.catalog.domain.exceptions.NotFoundException;
import com.gsv.codeflix.admin.catalog.domain.pagination.Pagination;
import com.gsv.codeflix.admin.catalog.domain.validation.Error;
import com.gsv.codeflix.admin.catalog.domain.validation.handler.Notification;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.CreateCategoryRequest;
import com.gsv.codeflix.admin.catalog.infrastructure.category.models.UpdateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @MockBean
    private GetCategoryListUseCase getCategoryListUseCase;

    // ------------------------------------------------------------------------------------------------
    // CreateCategory
    // ------------------------------------------------------------------------------------------------
    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryID() throws Exception {
        final var expectedName = "movies";
        final var expectedDesc = "The most loved category";
        final var expectedIsActive = true;

        final var input = new CreateCategoryRequest(expectedName, expectedDesc, expectedIsActive);

        when(createCategoryUseCase.execute(any())).thenReturn(Right(CreateCategoryOutput.from(CategoryID.from("123"))));

        final var request = MockMvcRequestBuilders.post("/categories").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request).andDo(print()).andExpectAll(status().isCreated(), header().string("Location", "/categories/123"));

        verify(createCategoryUseCase, times(1)).execute(argThat(arg -> Objects.equals(arg.name(), expectedName) && Objects.equals(arg.description(), expectedDesc) && Objects.equals(arg.isActive(), expectedIsActive)));
    }

    @Test
    public void givenAnInvalidCommand_whenCallsCreateCategory_shouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDesc = "The most loved category";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var input = new CreateCategoryRequest(expectedName, expectedDesc, expectedIsActive);
        final var notif = Notification.create(new Error(expectedMessage));
        when(createCategoryUseCase.execute(any())).thenReturn(Left(notif));

        final var request = MockMvcRequestBuilders.post("/categories").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request).andDo(print()).andExpect(status().isUnprocessableEntity()).andExpect(header().string("Location", nullValue())).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(arg -> Objects.equals(arg.name(), expectedName) && Objects.equals(arg.description(), expectedDesc) && Objects.equals(arg.isActive(), expectedIsActive)));
    }

    @Test
    public void givenAnUnexpectedProblem_whenCallsCreateCategory_shouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDesc = "The most loved category";
        final var expectedIsActive = true;
        final var expectedMessage = "unexpected error";

        final var input = new CreateCategoryRequest(expectedName, expectedDesc, expectedIsActive);
        final var error = new Error(expectedMessage);
        when(createCategoryUseCase.execute(any())).thenThrow(DomainException.with(error));

        final var request = MockMvcRequestBuilders.post("/categories").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(input));

        this.mvc.perform(request).andDo(print()).andExpect(status().isUnprocessableEntity()).andExpect(header().string("Location", nullValue())).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(arg -> Objects.equals(arg.name(), expectedName) && Objects.equals(arg.description(), expectedDesc) && Objects.equals(arg.isActive(), expectedIsActive)));
    }


    // ------------------------------------------------------------------------------------------------
    // GetCategoryById
    // ------------------------------------------------------------------------------------------------
    @Test
    public void givenAValidId_whenCallGetCategory_shouldReturnTheCategory() throws Exception {
        final var category = Category.newCategory("NAME", "DESC", true);
        final var expectedId = category.getId().getValue();
        final var categoryByIdOutput = GetCategoryByIdOutput.from(category);
        when(getCategoryByIdUseCase.execute(any())).thenReturn(categoryByIdOutput);

        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("");

        this.mvc.perform(request).andDo(print()).andExpect(status().isOk()).andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE)).andExpect(jsonPath("$.id", equalTo(category.getId().getValue())));

        verify(getCategoryByIdUseCase, times(1)).execute(expectedId);
    }

    @Test
    public void givenAValidId_whenCallGetCategory_shouldReturnNotFound() throws Exception {
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");

        when(getCategoryByIdUseCase.execute(any())).thenThrow(NotFoundException.with(Category.class, expectedId));

        final var request = MockMvcRequestBuilders.get("/categories/{id}", expectedId.getValue()).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request).andExpect(status().isNotFound()).andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

    // ------------------------------------------------------------------------------------------------
    // UpdateCategoryById
    // ------------------------------------------------------------------------------------------------
    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() throws Exception {
        // given
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(Right(UpdateCategoryOutput.from(expectedId)));

        final var input =
                new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final var expectedId = "123";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorCount = 1;
        final var expectedMessage = "'name' should not be null";

        when(updateCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        final var aCommand =
                new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(aCommand));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(expectedErrorCount)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() throws Exception {
        // given
        final var expectedId = "not-found";
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedErrorMessage = "Category with ID not-found was not found";

        when(updateCategoryUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, CategoryID.from(expectedId)));

        final var input =
                new UpdateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        // when
        final var request = put("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(updateCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }


    // ------------------------------------------------------------------------------------------------
    // DeleteCategoryById
    // ------------------------------------------------------------------------------------------------
    @Test
    public void givenARequestWithValidID_whenCallsDeleteCategory_shouldReturnCategoryId() throws Exception {
        // given
        final var expectedId = "1234";
        doNothing().when(deleteCategoryUseCase).execute(any());

        // when
        final var request = MockMvcRequestBuilders.delete("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isNoContent());

        verify(deleteCategoryUseCase, times(1)).execute(eq(expectedId));
    }


    // ------------------------------------------------------------------------------------------------
    // GetCategoryListUseCase
    // ------------------------------------------------------------------------------------------------
    @Test
    public void givenValidParams_whenCallsListCategories_shouldReturnCategories() throws Exception {
        // given
        final var aCategory = Category.newCategory("Movies", null, true);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "movies";
        final var expectedSort = "description";
        final var expectedDirection = "desc";
        final var expectedItemsCount = 1;
        final var expectedTotal = 1;

        final var expectedItems = List.of(GetCategoryListOutput.from(aCategory));

        when(getCategoryListUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));

        // when
        final var request = get("/categories")
                .queryParam("page", String.valueOf(expectedPage))
                .queryParam("perPage", String.valueOf(expectedPerPage))
                .queryParam("sort", expectedSort)
                .queryParam("dir", expectedDirection)
                .queryParam("search", expectedTerms)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(jsonPath("$.items", hasSize(expectedItemsCount)))
                .andExpect(jsonPath("$.items[0].id", equalTo(aCategory.getId().getValue())))
                .andExpect(jsonPath("$.items[0].name", equalTo(aCategory.getName())))
                .andExpect(jsonPath("$.items[0].description", equalTo(aCategory.getDescription())))
                .andExpect(jsonPath("$.items[0].is_active", equalTo(aCategory.getIsActive())))
                .andExpect(jsonPath("$.items[0].created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.items[0].deleted_at", equalTo(aCategory.getDeletedAt())));

        verify(getCategoryListUseCase, times(1)).execute(argThat(query ->
                Objects.equals(expectedPage, query.page())
                        && Objects.equals(expectedPerPage, query.perPage())
                        && Objects.equals(expectedDirection, query.direction())
                        && Objects.equals(expectedSort, query.sort())
                        && Objects.equals(expectedTerms, query.terms())
        ));
    }

}
