package com.fredsonchaves.infraestructure.category.update;

import com.fredsonchaves.application.category.update.UpdateCategoryInput;
import com.fredsonchaves.application.category.update.UpdateCategoryOutput;
import com.fredsonchaves.application.category.update.UpdateCategoryUseCase;
import com.fredsonchaves.config.annotations.IntegrationTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.handler.Notification;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class UpdateCategoryUseCaseIT {

    @Autowired
    private UpdateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @SpyBean
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCalsUpdateCategory_shouldReturnCategoryId() {
        final Category category = Category.newCategory("Film", null, true);
        save(category);
        assertEquals(1, repository.count());
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CategoryID expectedId = category.getId();
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        useCase.execute(input).get();
        final CategoryJpaEntity actualCategory =  repository.findById(expectedId.getValue()).get();
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    private void save(final Category... category) {
        repository.saveAllAndFlush(List.of(category).stream().map(CategoryJpaEntity::from).toList());
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final Category category = Category.newCategory("Film", null, true);
        save(category);
        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CategoryID expectedId = category.getId();
        final String expectedErrorMessage = "'name' should not be null";
        final int expectedErrorCount = 1;
        final UpdateCategoryInput createCategoryInput = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        final Notification notification = useCase.execute(createCategoryInput).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAInvalidCommandWithInactiveCategory_whenCallsUpdateCategory_thenShouldReturnCategoryId() {
        final Category category = Category.newCategory("Film", null, true);
        save(category);
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final CategoryID expectedId = category.getId();
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        final UpdateCategoryOutput outuput = useCase.execute(input).get();
        assertNotNull(outuput);
        assertNotNull(outuput.id());
        final CategoryJpaEntity actualCategory =  repository.findById(expectedId.getValue()).get();
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final Category category = Category.newCategory("Film", null, true);
        save(category);
        final CategoryID expectedId = category.getId();
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final String expectedErrorMessage = "Gateway error";
        final int expectedErrorCount = 1;
        final boolean expectedIsActive = true;
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        doThrow(new IllegalStateException(expectedErrorMessage)).when(gateway).update(any());
        final Notification notification = useCase.execute(input).getLeft();
        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.firstError().message());
        final CategoryJpaEntity actualCategory =  repository.findById(expectedId.getValue()).get();
        assertEquals("Film", actualCategory.getName());
        assertNull(actualCategory.getDescription());
        assertTrue(actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
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
        final DomainException actualException = assertThrows(
                DomainException.class,
                () -> useCase.execute(input)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
