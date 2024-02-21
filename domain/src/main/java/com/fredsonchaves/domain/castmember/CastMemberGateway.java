package com.fredsonchaves.domain.castmember;

import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(final CastMember genre);

    void deleteById(final CastMemberID id);

    Optional<CastMember> findById(final CastMemberID id);

    CastMember update(final CastMember genre);

    Pagination<CastMember> findAll(final SearchQuery query);
}
