package com.fredsonchaves.domain.category;

import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.handler.ThrowsValidationHandler;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final int expectedCountError = 1;
        final String expectedErrorMessage = "'name' should not be null";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        var actualException = assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
        assertEquals(expectedCountError, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = " ";
        final int expectedCountError = 1;
        final String expectedErrorMessage = "'name' should not be empty";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        var actualException = assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
        assertEquals(expectedCountError, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Fi ";
        final int expectedCountError = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        var actualException = assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
        assertEquals(expectedCountError, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidNameLengthMoreThan255Characters_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = """
        O empenho em analisar a mobilidade dos capitais internacionais agrega valor ao estabelecimento do sistema
        de formação de quadros que corresponde às necessidades.
        O empenho em analisar a mobilidade dos capitais internacionais agrega valor ao estabelecimento do sistema
        de formação de quadros que corresponde às necessidades.;
        """;
        final int expectedCountError = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        var actualException = assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
        assertEquals(expectedCountError, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Filmes";
        final String expectedDescription = " ";
        final boolean expectedIsActive = true;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        actualCategory.validate(new ThrowsValidationHandler());
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        actualCategory.validate(new ThrowsValidationHandler());
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final Category category = Category.newCategory(expectedName, expectedDescription, true);
        category.validate(new ThrowsValidationHandler());
        final Instant updatedAt = category.getUpdatedAt();
        final Instant createdAt = category.getCreatedAt();
        assertNotNull(category.getCreatedAt());
        assertNull(category.getDeletedAt());
        assertTrue(category.isActive());
        final Category actualCategory = category.deactivate();
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(category.getName(), actualCategory.getName());
        assertEquals(category.getDescription(), actualCategory.getDescription());
        assertEquals(category.getCreatedAt(), createdAt);
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory(expectedName, expectedDescription, false);
        category.validate(new ThrowsValidationHandler());
        final Instant updatedAt = category.getUpdatedAt();
        final Instant createdAt = category.getCreatedAt();
        assertNotNull(category.getCreatedAt());
        assertNotNull(category.getDeletedAt());
        assertFalse(category.isActive());
        final Category actualCategory = category.activate();
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(category.getName(), actualCategory.getName());
        assertEquals(category.getDescription(), actualCategory.getDescription());
        assertEquals(category.getCreatedAt(), createdAt);
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory("Film", "A Categoria", expectedIsActive);
        category.validate(new ThrowsValidationHandler());
        final Instant updatedAt = category.getUpdatedAt();
        final Instant createdAt = category.getCreatedAt();
        Category actualCategory = category.update(expectedName, expectedDescription, expectedIsActive);
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(category.getCreatedAt(), createdAt);
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = false;
        final Category category = Category.newCategory("Film", "A Categoria", true);
        category.validate(new ThrowsValidationHandler());
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());
        final Instant updatedAt = category.getUpdatedAt();
        final Instant createdAt = category.getCreatedAt();
        Category actualCategory = category.update(expectedName, expectedDescription, expectedIsActive);
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(category.getCreatedAt(), createdAt);
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertFalse(category.isActive());
        assertNotNull(category.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_theReturnCategoryUpdated() {
        final String expectedName = null;
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory("Filmes", "A Categoria", expectedIsActive);
        final Instant updatedAt = category.getUpdatedAt();
        final Instant createdAt = category.getCreatedAt();
        Category actualCategory = category.update(expectedName, expectedDescription, expectedIsActive);
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(category.getCreatedAt(), createdAt);
        assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        assertTrue(category.isActive());
        assertNull(category.getDeletedAt());
    }
}
