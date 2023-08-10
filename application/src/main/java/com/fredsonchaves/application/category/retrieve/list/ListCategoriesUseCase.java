package com.fredsonchaves.application.category.retrieve.list;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;

public interface ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {

    Pagination<CategoryListOutput> execute(final SearchQuery query);
}
