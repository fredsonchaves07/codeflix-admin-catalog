package com.fredsonchaves.domain.category;

import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    void deleteById(CategoryID categoryID);

    Optional<Category> findById(CategoryID categoryID);

    Category update(Category category);

    Pagination<Category> findAll(SearchQuery searchQuery);
}
