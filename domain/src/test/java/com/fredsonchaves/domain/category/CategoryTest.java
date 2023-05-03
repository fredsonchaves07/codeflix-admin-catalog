package com.fredsonchaves.domain.category;

import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.handler.ThrowsValidationHandler;
import org.junit.Test;

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
}
