package com.fredsonchaves.application.category.update;

import com.fredsonchaves.application.category.create.CreateCategoryInput;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.handler.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    public void givenAValidCommand_whenCalsUpdateCategory_shouldReturnCategoryId() {
        final Category category = Category.newCategory("Film", null, true);
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CategoryID expectedId = category.getId();
        final Instant expectedCreatedAt = category.getCreatedAt();
        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        final UpdateCategoryOutput outuput = useCase.execute(input).get();
        assertNotNull(outuput);
        assertNotNull(outuput.id());
        verify(gateway, times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(argThat(
                updatedCategory -> Objects.equals(expectedName, updatedCategory.getName())
                        && Objects.equals(expectedId, updatedCategory.getId())
                        && Objects.equals(expectedDescription, updatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, updatedCategory.isActive())
                        && Objects.equals(expectedCreatedAt, updatedCategory.getCreatedAt())
                        && category.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                        && Objects.isNull(updatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final Category category = Category.newCategory("Film", null, true);
        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CategoryID expectedId = category.getId();
        final String expectedErrorMessage = "'name' should not be null";
        final int expectedErrorCount = 1;
        final UpdateCategoryInput createCategoryInput = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(category.clone()));
        final Notification notification = useCase.execute(createCategoryInput).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAInvalidCommandWithInactiveCategory_whenCallsUpdateCategory_thenShouldReturnCategoryId() {
        final Category category = Category.newCategory("Film", null, true);
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final CategoryID expectedId = category.getId();
        final Instant expectedCreatedAt = category.getCreatedAt();
        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        final UpdateCategoryOutput outuput = useCase.execute(input).get();
        assertNotNull(outuput);
        assertNotNull(outuput.id());
        verify(gateway, times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(argThat(
                updatedCategory -> Objects.equals(expectedName, updatedCategory.getName())
                        && Objects.equals(expectedId, updatedCategory.getId())
                        && Objects.equals(expectedDescription, updatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, updatedCategory.isActive())
                        && Objects.equals(expectedCreatedAt, updatedCategory.getCreatedAt())
                        && category.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                        && Objects.nonNull(updatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final Category category = Category.newCategory("Film", null, true);
        final CategoryID expectedId = category.getId();
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final String expectedErrorMessage = "Gateway error";
        final int expectedErrorCount = 1;
        final boolean expectedIsActive = true;
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));
        final Notification notification = useCase.execute(input).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(gateway, times(1)).update(argThat(categoryUpdated ->
                Objects.equals(expectedName, categoryUpdated.getName())
                        && Objects.equals(expectedDescription, categoryUpdated.getDescription())
                        && Objects.equals(expectedIsActive, categoryUpdated.isActive())
                        && Objects.equals(expectedId, categoryUpdated.getId())
                        && Objects.nonNull(categoryUpdated.getCreatedAt())
                        && Objects.nonNull(categoryUpdated.getUpdatedAt())
                        && Objects.isNull(categoryUpdated.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidID_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final String expectedId = "123";
        final String expectedErrorMessage = "Category with id 123 was not found";
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId, expectedName, expectedDescription, expectedIsActive
        );
        when(gateway.findById(CategoryID.from(expectedId))).thenReturn(Optional.empty());
        final DomainException actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(input)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
        verify(gateway, times(1)).findById(eq(CategoryID.from(expectedId)));
        verify(gateway, times(0)).update(any());
    }
}
