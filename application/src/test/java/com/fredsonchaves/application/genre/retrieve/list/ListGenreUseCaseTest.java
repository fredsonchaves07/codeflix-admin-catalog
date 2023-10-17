package com.fredsonchaves.application.genre.retrieve.list;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private GenreListUseCase listGenreUseCase;

    @Mock
    private GenreGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidQueryWhenCallsListGenreShouldReturnGenres() {
        List<Genre> genres = List.of(
                Genre.newGenre("Ação", true),
                Genre.newGenre("Aventura", true)
        );
        final int expectedPage = 0;
        final int expectedPerPage = 10;
        final String exptectedTerms = "A";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";
        final int expectedTotal = genres.size();
        final var expectedItems = genres.stream().map(GenreListOutput::from).toList();
        final Pagination<Genre> expectedPagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, genres);
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, exptectedTerms, expectedSort, expectedDirection);
        when(gateway.findAll(any())).thenReturn(expectedPagination);
        final var actualOutput = listGenreUseCase.execute(query);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.items().size());
        assertEquals(expectedItems, actualOutput.items());
    }
}
