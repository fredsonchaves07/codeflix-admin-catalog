package com.fredsonchaves.application.category.create;


import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.validation.handler.Notification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase createCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCalsCreateCategory_shouldReturnCategoryId() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CreateCategoryInput createCategoryInput = CreateCategoryInput.with(
                expectedName, expectedDescription, expectedIsActive
        );
        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());
        final CreateCategoryOutput createCategoryOutput = createCategoryUseCase.execute(createCategoryInput).get();
        assertNotNull(createCategoryOutput.id());
        verify(categoryGateway, times(1)).create(argThat(category ->
                        Objects.equals(expectedName, category.getName())
                            && Objects.equals(expectedDescription, category.getDescription())
                            && Objects.equals(expectedIsActive, category.isActive())
                            && Objects.nonNull(category.getId())
                            && Objects.nonNull(category.getCreatedAt())
                            && Objects.nonNull(category.getUpdatedAt())
                            && Objects.isNull(category.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final String expectedErrorMessage = "'name' should not be null";
        final int expectedErrorCount = 1;
        final CreateCategoryInput createCategoryInput = CreateCategoryInput.with(
                expectedName, expectedDescription, expectedIsActive
        );
        final Notification notification = createCategoryUseCase.execute(createCategoryInput).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAInvalidCommandWithInactiveCategory_whenCallsCreateCategory_thenShouldReturnCategoryId() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final CreateCategoryInput createCategoryInput = CreateCategoryInput.with(
                expectedName, expectedDescription, expectedIsActive
        );
        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());
        final CreateCategoryOutput createCategoryOutput = createCategoryUseCase.execute(createCategoryInput).get();
        assertNotNull(createCategoryOutput.id());
        verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.nonNull(category.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final String expectedErrorMessage = "Gateway error";
        final int expectedErrorCount = 1;
        final boolean expectedIsActive = true;
        final CreateCategoryInput createCategoryInput = CreateCategoryInput.with(
                expectedName, expectedDescription, expectedIsActive
        );
        when(categoryGateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));
        final Notification notification = createCategoryUseCase.execute(createCategoryInput).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.isNull(category.getDeletedAt())
        ));
    }
}
