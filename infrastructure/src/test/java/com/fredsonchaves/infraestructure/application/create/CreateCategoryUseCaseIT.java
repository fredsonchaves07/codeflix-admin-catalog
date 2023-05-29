package com.fredsonchaves.infraestructure.application.create;

import com.fredsonchaves.IntegrationTest;
import com.fredsonchaves.application.category.create.CreateCategoryInput;
import com.fredsonchaves.application.category.create.CreateCategoryOutput;
import com.fredsonchaves.application.category.create.CreateCategoryUseCase;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class CreateCategoryUseCaseIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @SpyBean
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCalsCreateCategory_shouldReturnCategoryId() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        assertEquals(0, repository.count());
        final CreateCategoryInput createCategoryInput = CreateCategoryInput.with(
                expectedName, expectedDescription, expectedIsActive
        );
        final CreateCategoryOutput actualOutput = useCase.execute(createCategoryInput).get();
        assertEquals(1, repository.count());
        final CategoryJpaEntity actualCategory =  repository.findById(actualOutput.id().getValue()).get();
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
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
        assertEquals(0, repository.count());
        final Notification notification = useCase.execute(createCategoryInput).getLeft();
        assertEquals(0, repository.count());
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
    }

    @Test
    public void givenAInvalidCommandWithInactiveCategory_whenCallsCreateCategory_thenShouldReturnCategoryId() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final CreateCategoryInput createCategoryInput = CreateCategoryInput.with(
                expectedName, expectedDescription, expectedIsActive
        );
        assertEquals(0, repository.count());
        final CreateCategoryOutput actualOutput = useCase.execute(createCategoryInput).get();
        assertNotNull(actualOutput.id());
        assertEquals(1, repository.count());
        final CategoryJpaEntity actualCategory =  repository.findById(actualOutput.id().getValue()).get();
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());
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
        doThrow(new IllegalStateException(expectedErrorMessage)).when(gateway).create(any());
        final Notification notification = useCase.execute(createCategoryInput).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
    }
}
