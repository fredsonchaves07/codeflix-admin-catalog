package com.fredsonchaves.infraestructure.category;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.category.CategorySearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {


    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    public void deleteById(CategoryID categoryID) {

    }

    @Override
    public Optional<Category> findById(CategoryID categoryID) {
        return Optional.empty();
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery categorySearchQuery) {
        return null;
    }
}
