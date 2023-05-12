package com.fredsonchaves.application.category.retrieve.list;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.category.CategorySearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;

public interface ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {

    Pagination<CategoryListOutput> execute(final CategorySearchQuery query);
}
