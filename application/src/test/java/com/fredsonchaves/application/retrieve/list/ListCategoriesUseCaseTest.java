package com.fredsonchaves.application.retrieve.list;

import com.fredsonchaves.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.fredsonchaves.application.category.retrieve.list.CategoryListOutput;
import com.fredsonchaves.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategorySearchQuery;
import com.fredsonchaves.domain.pagination.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private DefaultListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(gateway);
    }

    @Test
    public void givenAValidQuery_whenCallsListCategories_thenShouldReturnCategoriesList() {
        List<Category> categories = List.of(
                Category.newCategory("Filmes", null, true),
                Category.newCategory("S�ries", null, true)
        );
        final int expectedPage = 0;
        final int expectedPerPage = 0;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";

        CategorySearchQuery query = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );
        Pagination<Category> expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);
        final int expectedItemsCount = 2;
        final Pagination<CategoryListOutput> expectedResults = expectedPagination.map(CategoryListOutput::from);
        when(gateway.findAll(eq(query))).thenReturn(expectedPagination);
        final var actualResult = useCase.execute(query);
        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedResults, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(categories.size(), actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyCategoriesList() {
        List<Category> categories = List.of();
        final int expectedPage = 0;
        final int expectedPerPage = 0;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";

        CategorySearchQuery query = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );
        Pagination<Category> expectedPagination = new Pagination<>(expectedPage, expectedPerPage, 0, categories);
        final int expectedItemsCount = 0;
        final Pagination<CategoryListOutput> expectedResults = expectedPagination.map(CategoryListOutput::from);
        when(gateway.findAll(eq(query))).thenReturn(expectedPagination);
        final var actualResult = useCase.execute(query);
        assertEquals(expectedItemsCount, actualResult.items().size());
        assertEquals(expectedResults, actualResult);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(0, actualResult.total());
    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_shouldReturnException() {
        final int expectedPage = 0;
        final int expectedPerPage = 0;
        final String expectedTerms = "";
        final String expectedSort = "createdAt";
        final String expectedDirection = "asc";
        final String expectedErrorMessage = "Gateway error";

        CategorySearchQuery query = new CategorySearchQuery(
                expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection
        );
        when(gateway.findAll(eq(query))).thenThrow(new IllegalStateException(expectedErrorMessage));
        final IllegalStateException actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(query)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
