package com.fredsonchaves.application.castmember.retrieve.list;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberType;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ListCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private ListCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway gateway;


    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCastMembers_thenShouldReturnCastMemberList() {
        List<CastMember> castMembers = List.of(
                CastMember.newMember("Van Dielsel", CastMemberType.ACTOR),
                CastMember.newMember("Director", CastMemberType.DIRECTOR)
        );
        final int expectedPage = 0;
        final int expectedPerPage = 0;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";

        SearchQuery query = new SearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );
        Pagination<CastMember> expectedPagination = new Pagination<>(expectedPage, expectedPerPage, castMembers.size(), castMembers);
        final int expectedItemsCount = 2;
        final Pagination<CastMemberListOutput> expectedResults = expectedPagination.map(CastMemberListOutput::from);
        when(gateway.findAll(eq(query))).thenReturn(expectedPagination);
        final var actualResult = useCase.execute(query);
        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedResults, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(castMembers.size(), actualResult.total());
    }
}
