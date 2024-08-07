package codeflixadmincatalog.domain.category;

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
}
