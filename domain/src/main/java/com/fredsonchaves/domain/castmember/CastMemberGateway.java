package com.fredsonchaves.domain.castmember;

import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(final Genre genre);

    void deleteById(final GenreID id);

    Optional<CastMember> findById(final GenreID id);

    CastMember update(final Genre genre);

    Pagination<CastMember> findAll(final SearchQuery query);
}
