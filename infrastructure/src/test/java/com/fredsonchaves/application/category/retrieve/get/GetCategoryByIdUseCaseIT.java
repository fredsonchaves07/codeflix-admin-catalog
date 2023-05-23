package com.fredsonchaves.application.category.retrieve.get;

import com.fredsonchaves.IntegrationTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@IntegrationTest
public class GetCategoryByIdUseCaseIT {

    @Autowired
    private GetCategoryByIdUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @SpyBean
    private CategoryGateway gateway;

    @Test
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        CategoryID expectedId = category.getId();
        save(category);
        CategoryOutput actualCategory = useCase.execute(expectedId);
        assertEquals(expectedId, actualCategory.id());
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.createdAt());
        assertNotNull(actualCategory.updatedAt());
        assertNull(actualCategory.deletedAt());
    }

    private void save(final Category... category) {
        repository.saveAllAndFlush(List.of(category).stream().map(CategoryJpaEntity::from).toList());
    }

    @Test
    public void givenAInvalidId_whenCallsGetCategory_shouldBeReturnNotFound() {
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        final String expectedErrorMessage = "Category with id %s was not found".formatted(expectedId.getValue());
        when(gateway.findById(expectedId)).thenReturn(Optional.empty());
        final DomainException actualException = assertThrows(
                DomainException.class,
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
