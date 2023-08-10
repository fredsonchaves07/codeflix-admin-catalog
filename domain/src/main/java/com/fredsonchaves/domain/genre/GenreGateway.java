package com.fredsonchaves.domain.genre;

import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;

import java.util.Optional;

public interface GenreGateway {

    Genre create(final Genre genre);

    void deleteById(final GenreID id);

    Optional<Genre> findById(final GenreID id);

    Genre update(final Genre genre);

    Pagination<Genre> findAll(final SearchQuery query);
}
