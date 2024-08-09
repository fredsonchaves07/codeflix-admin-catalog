package codeflixadmincatalog.domain.entities.category;

import codeflixadmincatalog.domain.errors.category.CategoryError;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static codeflixadmincatalog.factories.entities.MakeCategory.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryEntityTest {

    @Test
    public void shouldCreateANewCategory() {
        final Category category = makeCategory();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        assertNotNull(newCategory);
        assertNotNull(newCategory.id());
        assertEquals(category.name(), newCategory.name());
        assertEquals(category.description(), newCategory.description());
        assertEquals(category.isActive(), newCategory.isActive());
        assertNotNull(newCategory.createdAt());
        assertNotNull(newCategory.updatedAt());
        assertNull(newCategory.deletedAt());
    }

    @Test
    public void shouldNotCreateANewCategoryWithInvalidNullName() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name cannot be null";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> Category.create(null, category.description(), category.isActive())
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithEmptyNullName() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name cannot be empty";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> Category.create("", category.description(), category.isActive())
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameLessThan3Characters() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> Category.create("ca", category.description(), category.isActive())
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameMoreThan50Characters() {
        final String name = "Este é um exemplo de uma string longa com mais de 50 caracteres em Java, " +
                "para ilustrar a geração de strings extensas.";
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> Category.create(name, category.description(), category.isActive())
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldCreateANewCategoryWithEmptyDescription() {
        final Category category = makeCategoryWithEmptyDescription();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        assertNotNull(newCategory);
        assertNotNull(newCategory.id());
        assertEquals(category.name(), newCategory.name());
        assertEquals(category.description(), newCategory.description());
        assertEquals(category.isActive(), newCategory.isActive());
        assertNotNull(newCategory.createdAt());
        assertNotNull(newCategory.updatedAt());
        assertNull(newCategory.deletedAt());
    }

    @Test
    public void shouldCreateANewCategoryWithInactive() {
        final Category category = makeCategoryWithInactive();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        assertNotNull(newCategory);
        assertNotNull(newCategory.id());
        assertEquals(category.name(), newCategory.name());
        assertEquals(category.description(), newCategory.description());
        assertEquals(category.isActive(), newCategory.isActive());
        assertNotNull(newCategory.createdAt());
        assertNotNull(newCategory.updatedAt());
        assertNull(newCategory.deletedAt());
    }

    @Test
    public void shouldInactivatedCategoryIfDeactivateCategory() {
        final Category category = makeCategory();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        LocalDateTime newCategoryUpdatedAt = newCategory.updatedAt();
        newCategory.deactivate();
        assertFalse(newCategory.isActive());
        assertNotEquals(newCategoryUpdatedAt, newCategory.updatedAt());
    }

    @Test
    public void shouldActivatedCategoryIfActivatedCategory() {
        final Category category = makeCategoryWithInactive();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        LocalDateTime newCategoryUpdatedAt = newCategory.updatedAt();
        newCategory.activate();
        assertTrue(newCategory.isActive());
        assertNotEquals(newCategoryUpdatedAt, newCategory.updatedAt());
    }

    @Test
    public void shouldUpdateACategoryName() {
        final Category category = makeCategory();
        final String name = "Filme";
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        LocalDateTime newCategoryUpdatedAt = newCategory.updatedAt();
        newCategory.name(name);
        assertEquals(name, newCategory.name());
        assertNotEquals(newCategoryUpdatedAt, newCategory.updatedAt());
    }

    @Test
    public void shouldNotUpdateACategoryWithInvalidNullName() {
        final Category category = makeCategory();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        final String expectedErrorMessage = "Category name cannot be null";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> newCategory.name(null)
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldNotUpdateACategoryWithIEmptyNullName() {
        final Category category = makeCategory();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        final String expectedErrorMessage =  "Category name cannot be empty";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> newCategory.name("")
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldNotUpdateACategoryWithLengthNameLessThan3Characters() {
        final Category category = makeCategory();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> newCategory.name("ca")
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldNotUpdateACategoryWithLengthNameMoreThan50Characters() {
        final String name = "Este é um exemplo de uma string longa com mais de 50 caracteres em Java, " +
                "para ilustrar a geração de strings extensas.";
        final Category category = makeCategory();
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final CategoryError categoryError = assertThrows(
                CategoryError.class,
                () -> newCategory.name(name)
        );
        assertTrue(categoryError.isDomainError());
        assertEquals(expectedErrorMessage, categoryError.getMessage());
    }

    @Test
    public void shouldUpdateACategoryDescription() {
        final Category category = makeCategoryWithInactive();
        final String description = "Filme mais assistido";
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        LocalDateTime newCategoryUpdatedAt = newCategory.updatedAt();
        newCategory.description(description);
        assertEquals(description, newCategory.description());
        assertNotEquals(newCategoryUpdatedAt, newCategory.updatedAt());
    }

    @Test
    public void shouldUpdateACategory() {
        final Category category = makeCategoryWithInactive();
        final String name = "Filme";
        final String description = "Filme mais assistido";
        final Category newCategory = Category.create(category.name(), category.description(), category.isActive());
        LocalDateTime newCategoryUpdatedAt = newCategory.updatedAt();
        newCategory.name(name);
        newCategory.description(description);
        assertEquals(name, newCategory.name());
        assertEquals(description, newCategory.description());
        assertNotEquals(newCategoryUpdatedAt, newCategory.updatedAt());
    }
}
