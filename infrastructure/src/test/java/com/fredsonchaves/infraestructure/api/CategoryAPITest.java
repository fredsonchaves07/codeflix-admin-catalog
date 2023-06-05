package com.fredsonchaves.infraestructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves.ControllerTest;
import com.fredsonchaves.application.category.create.CreateCategoryOutput;
import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.application.category.delete.DeleteCategoryUseCase;
import com.fredsonchaves.application.category.retrieve.get.CategoryOutput;
import com.fredsonchaves.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.fredsonchaves.application.category.retrieve.list.ListCategoriesUseCase;
import com.fredsonchaves.application.category.update.UpdateCategoryOutput;
import com.fredsonchaves.application.category.update.UpdateCategoryUseCase;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.exceptions.NotFoundException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.infraestructure.category.models.CreateCategoryApiInput;
import com.fredsonchaves.infraestructure.category.models.UpdateCategoryApiInput;
import io.vavr.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
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
    private ListCategoriesUseCase listCategoriesUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @Test
    public void givenAValidCommand_whenCalsCreateCategory_shouldReturnCategoryId() throws Exception {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CreateCategoryApiInput createCategoryInput = new CreateCategoryApiInput(
                expectedName, expectedDescription, expectedIsActive
        );
        when(createCategoryUseCase.execute(any())).thenReturn(Right(CreateCategoryOutput.from("123")));
        MockHttpServletRequestBuilder request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryInput));
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/categories/123")
                );
        verify(createCategoryUseCase, times(1)).execute(argThat(output ->
                Objects.equals(expectedName, output.name())
                && Objects.equals(expectedDescription, output.description())
                && Objects.equals(expectedIsActive, output.isActive())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CreateCategoryApiInput createCategoryInput = new CreateCategoryApiInput(
                expectedName, expectedDescription, expectedIsActive
        );
        when(createCategoryUseCase.execute(any())).thenReturn(Left(Notification.create(new Error("'name' should not be null"))));
        MockHttpServletRequestBuilder request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryInput));
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Location", nullValue()),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo("'name' should not be null"))
                );
        verify(createCategoryUseCase, times(1)).execute(argThat(output ->
                Objects.equals(expectedName, output.name())
                        && Objects.equals(expectedDescription, output.description())
                        && Objects.equals(expectedIsActive, output.isActive())
        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CreateCategoryApiInput createCategoryInput = new CreateCategoryApiInput(
                expectedName, expectedDescription, expectedIsActive
        );
        when(createCategoryUseCase.execute(any())).thenThrow(DomainException.with(new Error("'name' should not be null")));
        MockHttpServletRequestBuilder request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryInput));
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        header().string("Location", nullValue()),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo("'name' should not be null"))
                );
        verify(createCategoryUseCase, times(1)).execute(argThat(output ->
                Objects.equals(expectedName, output.name())
                        && Objects.equals(expectedDescription, output.description())
                        && Objects.equals(expectedIsActive, output.isActive())
        ));
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        CategoryID expectedId = category.getId();
        when(getCategoryByIdUseCase.execute(expectedId)).thenReturn(CategoryOutput.from(category));
        MockHttpServletRequestBuilder request = get("/categories/{id}", expectedId.getValue());
        mvc.perform(request).andDo(print()).andExpectAll(
                status().isOk(),
                jsonPath("$.id", equalTo(expectedId.getValue())),
                jsonPath("$.name", equalTo(expectedName)),
                jsonPath("$.description", equalTo(expectedDescription)),
                jsonPath("$.is_active", equalTo(expectedIsActive))
        );
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldBeReturnNotFound() throws Exception{
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        final String expectedErrorMessage = "%s with ID %s was not found".formatted(Category.class.getSimpleName(), expectedId.getValue());
        when(getCategoryByIdUseCase.execute(any())).thenThrow(NotFoundException.with(Category.class, expectedId));
        MockHttpServletRequestBuilder request = get("/categories/{id}", expectedId);
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message", equalTo(expectedErrorMessage)
                ));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldReturnException() throws Exception {
        final String expectedErrorMessage = "Gateway error";
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        when(getCategoryByIdUseCase.execute(any())).thenThrow(DomainException.with(new Error(expectedErrorMessage)));
        MockHttpServletRequestBuilder request = get("/categories/{id}", expectedId);
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isUnprocessableEntity(),
                        jsonPath("$.errors", hasSize(1)),
                        jsonPath("$.errors[0].message", equalTo(expectedErrorMessage))
                );
    }

    @Test
    public void givenAValidCommand_whenCalsUpdateCategory_shouldReturnCategoryId() throws Exception {
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        when(updateCategoryUseCase.execute(any())).thenReturn(API.Right(UpdateCategoryOutput.from(expectedId)));
        final UpdateCategoryApiInput updateCategoryApiInput = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        MockHttpServletRequestBuilder request = put("/categories/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateCategoryApiInput));
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                    status().isOk()
                );
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() throws Exception {
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final String expectedErrorMessage = "Category with ID " + expectedId.getValue() + " was not found";
        when(updateCategoryUseCase.execute(any())).thenThrow(NotFoundException.with(Category.class, expectedId));
        final UpdateCategoryApiInput updateCategoryApiInput = new UpdateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);
        MockHttpServletRequestBuilder request = put("/categories/{id}", expectedId.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateCategoryApiInput));;
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message", equalTo(expectedErrorMessage)));
    }
}
