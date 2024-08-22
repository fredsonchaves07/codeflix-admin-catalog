package github.fredsonchaves07.codeflix.admin.catalog.category.database.jpa;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.entities.category.CategoryID;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static github.fredsonchaves07.codeflix.admin.catalog.factories.entities.MakeCategory.makeCategory;
import static github.fredsonchaves07.codeflix.admin.catalog.factories.entities.MakeCategory.makeCategoryWithInactive;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CategoryJpaTest {

    @Inject
    CategoryRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateACategory() {
        Category category = makeCategory();
        assertDoesNotThrow(() -> repository.save(category));
        assertEquals(Long.valueOf(1), repository.count());
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
        assertEquals(Long.valueOf(1), repository.count());
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
    public void notShouldGetCategoryIfCategoryDoesNotExists() {
        Optional<Category> category = repository.findById(CategoryID.newId());
        assertFalse(category.isPresent());
    }

    @Test
    public void shouldFindAllCategories() {
        repository.save(makeCategory());
        repository.save(makeCategory());
        repository.save(makeCategory());
        repository.save(makeCategory());
        assertFalse(repository.findAll().isEmpty());
        assertEquals(Integer.valueOf(4), repository.findAll().size());
        assertEquals(Long.valueOf(4), repository.count());
    }

    @Test
    public void shouldDeleteCategory() {
        Category category = makeCategory();
        repository.save(category);
        assertDoesNotThrow(() -> repository.delete(category));
        assertEquals(Long.valueOf(0), repository.count());
    }

    @Test
    public void shouldDeleteAllCategories() {
        repository.save(makeCategory());
        repository.save(makeCategory());
        repository.save(makeCategory());
        repository.save(makeCategory());
        assertDoesNotThrow(() -> repository.deleteAll());
        assertEquals(Long.valueOf(0), repository.count());
    }
}