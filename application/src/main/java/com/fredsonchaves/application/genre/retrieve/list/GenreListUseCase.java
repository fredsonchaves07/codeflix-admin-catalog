package com.fredsonchaves.application.genre.retrieve.list;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;

public class GenreListUseCase implements UseCase<SearchQuery, Pagination<GenreListOutput>> {

    final GenreGateway gateway;

    public GenreListUseCase(GenreGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public Pagination<GenreListOutput> execute(SearchQuery searchQuery) {
        return gateway.findAll(searchQuery).map(GenreListOutput::from);
    }
}
