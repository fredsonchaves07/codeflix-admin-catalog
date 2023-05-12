package com.fredsonchaves.application.category.retrieve.list;

import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategorySearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase implements ListCategoriesUseCase{

    private final CategoryGateway gateway;

    public DefaultListCategoriesUseCase(final CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery query) {
        return gateway.findAll(query).map(CategoryListOutput::from);
    }
}
