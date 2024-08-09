package codeflixadmincatalog.domain.repositories.category;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.factories.db.repositories.category.InMemoryCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static codeflixadmincatalog.factories.entities.MakeCategory.makeCategory;
import static codeflixadmincatalog.factories.entities.MakeCategory.makeCategoryWithInactive;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryTest {

    CategoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = InMemoryCategoryRepository.createRepository();
    }

    @Test
    public void shouldCreateCategory() {
        Category category = makeCategory();
        assertDoesNotThrow(() -> repository.save(category));
        assertEquals(Integer.valueOf(1), repository.count());
    }

    @Test
    public void shouldUpdateCategory() {
        Category category = makeCategoryWithInactive();
        Category categoryUpdate = makeCategory();
        assertDoesNotThrow(() -> repository.save(category));
        category.name(categoryUpdate.name());
        category.description(categoryUpdate.description());
        category.activate();
        assertDoesNotThrow(() -> repository.save(category));
        assertEquals(Integer.valueOf(1), repository.count());
    }

    @Test
    public void shouldGetCategory() {
        Category newCategory = makeCategory();
        repository.save(newCategory);
        Optional<Category> category = repository.findById(newCategory.id());
        assertTrue(category.isPresent());
        assertEquals(newCategory, category.get());
        assertEquals(newCategory.id(), category.get().id());
        assertEquals(newCategory.name(), category.get().name());
        assertEquals(newCategory.description(), category.get().description());
        assertEquals(newCategory.isActive(), category.get().isActive());
        assertEquals(newCategory.createdAt(), category.get().createdAt());
        assertEquals(newCategory.updatedAt(), category.get().updatedAt());
    }

    @Test
    public void shouldFindAllCategories() {
        repository.save(makeCategory());
        repository.save(makeCategory());
        repository.save(makeCategory());
        repository.save(makeCategory());
        assertFalse(repository.findAll().isEmpty());
        assertEquals(Integer.valueOf(4), repository.findAll().size());
        assertEquals(Integer.valueOf(4), repository.count());
    }

    @Test
    public void shouldDeleteCategory() {
        Category category = makeCategory();
        repository.save(category);
        assertDoesNotThrow(() -> repository.delete(category));
        assertEquals(Integer.valueOf(0), repository.count());
    }
}
