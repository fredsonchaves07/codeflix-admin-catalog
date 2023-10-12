package com.fredsonchaves.application.category.retrieve.get;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.application.category.retrieve.get.CategoryOutput;
import com.fredsonchaves.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        CategoryID expectedId = category.getId();
        Instant expectedCreatedAt = category.getCreatedAt();
        Instant expectedUpdatedAt = category.getUpdatedAt();
        when(gateway.findById(expectedId)).thenReturn(Optional.of(category.clone()));
        CategoryOutput actualCategory = useCase.execute(expectedId);
        assertEquals(CategoryOutput.from(category), actualCategory);
        assertEquals(expectedId, actualCategory.id());
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(expectedCreatedAt, actualCategory.createdAt());
        assertEquals(expectedUpdatedAt, actualCategory.updatedAt());
        assertNull(actualCategory.deletedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldBeReturnNotFound() {
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        final String expectedErrorMessage = "Category with ID %s was not found".formatted(expectedId.getValue());
        when(gateway.findById(expectedId)).thenReturn(Optional.empty());
        final NotFoundException actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldReturnException() {
        final String expectedErrorMessage = "Gateway error";
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        when(gateway.findById(expectedId)).thenThrow(new IllegalStateException(expectedErrorMessage));
        final IllegalStateException actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
