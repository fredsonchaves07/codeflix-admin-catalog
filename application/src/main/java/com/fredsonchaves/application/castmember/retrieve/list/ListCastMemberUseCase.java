package com.fredsonchaves.application.castmember.retrieve.list;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;

import java.util.Objects;

public class ListCastMemberUseCase implements UseCase<SearchQuery, Pagination<CastMemberListOutput>> {

    private final CastMemberGateway gateway;

    public ListCastMemberUseCase(final CastMemberGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(final SearchQuery query) {
        return gateway.findAll(query).map(CastMemberListOutput::from);
    }
}
