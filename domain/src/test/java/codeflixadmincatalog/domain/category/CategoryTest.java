package codeflixadmincatalog.domain.category;

import codeflixadmincatalog.core.errors.DomainError;
import codeflixadmincatalog.domain.category.entity.Category;
import org.junit.jupiter.api.Test;

import static codeflixadmincatalog.factories.utils.MakeCategory.makeCategory;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

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
        final DomainError domainError = assertThrows(
                DomainError.class,
                () -> Category.create(null, category.description(), category.isActive())
        );
        assertEquals(expectedErrorMessage, domainError.getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithEmptyNullName() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name cannot be empty";
        final DomainError domainError = assertThrows(
                DomainError.class,
                () -> Category.create("", category.description(), category.isActive())
        );
        assertEquals(expectedErrorMessage, domainError.getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameLessThan3Characters() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final DomainError domainError = assertThrows(
                DomainError.class,
                () -> Category.create("ca", category.description(), category.isActive())
        );
        assertEquals(expectedErrorMessage, domainError.getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameMoreThan50Characters() {
        final String name = "Este é um exemplo de uma string longa com mais de 50 caracteres em Java, " +
                "para ilustrar a geração de strings extensas.";
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final DomainError domainError = assertThrows(
                DomainError.class,
                () -> Category.create(name, category.description(), category.isActive())
        );
        assertEquals(expectedErrorMessage, domainError.getMessage());
    }
}
