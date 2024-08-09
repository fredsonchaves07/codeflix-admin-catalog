package codeflixadmincatalog.factories.db.repositories.category;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.entities.category.CategoryID;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

public class FailingCategoryRepository implements CategoryRepository {

    public static CategoryRepository createRepository() {
        return new FailingCategoryRepository();
    }

    @Override
    public void save(Category entity) {
        throw new RuntimeException("An error occurred while running the repository");
    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        throw new RuntimeException("An error occurred while running the repository");
    }

    @Override
    public List<Category> findAll() {
        throw new RuntimeException("An error occurred while running the repository");
    }

    @Override
    public void delete(Category entity) {
        throw new RuntimeException("An error occurred while running the repository");
    }
}
