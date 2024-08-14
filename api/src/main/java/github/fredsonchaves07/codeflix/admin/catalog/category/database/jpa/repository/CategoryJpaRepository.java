package github.fredsonchaves07.codeflix.admin.catalog.category.database.jpa.repository;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.entities.category.CategoryID;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;
import github.fredsonchaves07.codeflix.admin.catalog.category.database.jpa.entity.CategoryJpa;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryJpaRepository implements CategoryRepository {

    @Override
    @Transactional
    public void save(Category entity) {
        Optional<CategoryJpa> category = findCategoryJpaById(entity.id());
        if (category.isEmpty()) CategoryJpa.from(entity).persist();
        category.ifPresent(categoryJpa -> categoryJpa.updateFrom(entity).persist());
    }

    private Optional<CategoryJpa> findCategoryJpaById(CategoryID id) {
        return Optional.ofNullable(CategoryJpa.findById(id.toString()));
    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        Optional<CategoryJpa> category = findCategoryJpaById(id);
        return category.map(CategoryJpa::toAggregate);
    }

    @Override
    public List<Category> findAll() {
        PanacheQuery<CategoryJpa> categories = CategoryJpa.findAll();
        return categories.stream().map(CategoryJpa::toAggregate).toList();
    }

    @Override
    @Transactional
    public void delete(Category entity) {
        Optional<CategoryJpa> category = findCategoryJpaById(entity.id());
        category.ifPresent(CategoryJpa::delete);
    }

    @Override
    @Transactional
    public void deleteAll() {
        CategoryJpa.deleteAll();
    }

    @Override
    public long count() {
        return CategoryJpa.count();
    }
}
