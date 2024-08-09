package codeflixadmincatalog.factories.db.repositories.category;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.entities.category.CategoryID;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;
import codeflixadmincatalog.factories.db.DB;

import java.util.List;
import java.util.Optional;

import static codeflixadmincatalog.factories.db.MemoryDB.createDB;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final DB<Category> db;

    private InMemoryCategoryRepository() {
        db = createDB();
    }

    public static CategoryRepository createRepository() {
        return new InMemoryCategoryRepository();
    }

    @Override
    public void save(Category entity) {
        db.add(entity.id().toString(), entity);
    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        return db.findById(id.toString());
    }

    @Override
    public List<Category> findAll() {
        return db.findAll();
    }

    @Override
    public void delete(Category entity) {
        db.delete(entity.id().toString());
    }
}
