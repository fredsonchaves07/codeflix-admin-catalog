package com.fredsonchaves.infraestructure.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredsonchaves.ControllerTest;
import com.fredsonchaves.IntegrationTest;
import com.fredsonchaves.application.category.create.CreateCategoryInput;
import com.fredsonchaves.application.category.create.CreateCategoryOutput;
import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.infraestructure.category.models.CreateCategoryApiInput;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import io.vavr.API;
import io.vavr.control.Either;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCategoryUseCase useCase;

    @Test
    public void givenAValidCommand_whenCalsCreateCategory_shouldReturnCategoryId() throws Exception {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CreateCategoryApiInput createCategoryInput = new CreateCategoryApiInput(
                expectedName, expectedDescription, expectedIsActive
        );
        when(useCase.execute(any())).thenReturn(Right(CreateCategoryOutput.from("123")));
        MockHttpServletRequestBuilder request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createCategoryInput));
        mvc.perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", "/categories/123")
                );
        verify(useCase, times(1)).execute(argThat(output ->
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
        when(useCase.execute(any())).thenReturn(Left(Notification.create(new Error("'name' should not be null"))));
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
        verify(useCase, times(1)).execute(argThat(output ->
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
        when(useCase.execute(any())).thenThrow(DomainException.with(new Error("'name' should not be null")));
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
        verify(useCase, times(1)).execute(argThat(output ->
                Objects.equals(expectedName, output.name())
                        && Objects.equals(expectedDescription, output.description())
                        && Objects.equals(expectedIsActive, output.isActive())
        ));
    }
}
