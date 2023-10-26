package com.fredsonchaves.infraestructure.genre;

import com.fredsonchaves.config.annotations.MySQLGatewayTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.category.CategoryMySQLGateway;
import com.fredsonchaves.infraestructure.genre.persistence.GenreJpaEntity;
import com.fredsonchaves.infraestructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@MySQLGatewayTest
public class GenreMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private GenreMySQLGateway genreMySQLGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidGenreWithCategoryWhenCallsCreateGenreShouldPersistGenre() {
        Category category = categoryMySQLGateway.create(Category.newCategory("Filmes", null, true));
        final String expectedName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(category.getId());
        Genre genre = Genre.newGenre(expectedName, isActive);
        genre.addCategories(expectedCategories);
        assertEquals(0, genreRepository.count());
        genre = genreMySQLGateway.create(genre);
        assertEquals(expectedName, genre.getName());
        assertTrue(genre.isActive());
        assertEquals(expectedCategories, genre.getCategories());
        GenreJpaEntity genreJpaEntity = genreRepository.findById(genre.getId().getValue()).get();
        assertEquals(expectedName, genreJpaEntity.getName());
        assertTrue(genreJpaEntity.isActive());
        assertEquals(expectedCategories, genreJpaEntity.getCategoryIds());
    }

    @Test
    public void givenAValidGenreWhithoutCategoryWhenCallsCreateGenreShouldPersistGenre() {
        final String expectedName = "Ação";
        final boolean isActive = true;
        Genre genre = Genre.newGenre(expectedName, isActive);
        assertEquals(0, genreRepository.count());
        genre = genreMySQLGateway.create(genre);
        assertEquals(expectedName, genre.getName());
        assertTrue(genre.isActive());
        assertTrue(genre.getCategories().isEmpty());
        GenreJpaEntity genreJpaEntity = genreRepository.findById(genre.getId().getValue()).get();
        assertEquals(expectedName, genreJpaEntity.getName());
        assertTrue(genreJpaEntity.isActive());
        assertTrue(genreJpaEntity.getCategoryIds().isEmpty());
    }

    @Test
    public void givenAValidGenreWhenCallsUpdateShouldReturnAGenreUptaded() {
        Category category = categoryMySQLGateway.create(Category.newCategory("Filmes", null, true));
        final String expectedName = "Test";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(category.getId());
        Genre genre = Genre.newGenre("Ação", false);
        genreMySQLGateway.create(genre);
        genre.update(expectedName, isActive, expectedCategories);
        genreMySQLGateway.update(genre);
        GenreJpaEntity genreJpaEntity = genreRepository.findById(genre.getId().getValue()).get();
        assertEquals(expectedName, genreJpaEntity.getName());
        assertTrue(genreJpaEntity.isActive());
        assertFalse(genreJpaEntity.getCategoryIds().isEmpty());
    }

    @Test
    public void givenAValidGenreWhenCallsDeleteShouldDeleteGenre() {
        Category category = categoryMySQLGateway.create(Category.newCategory("Filmes", null, true));
        final String expectedName = "Test";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(category.getId());
        Genre genre = Genre.newGenre(expectedName, isActive);
        genre.addCategories(expectedCategories);
        assertEquals(0, genreRepository.count());
        String genreId = genreRepository.saveAndFlush(GenreJpaEntity.from(genre)).getId();
        assertEquals(1, genreRepository.count());
        genreMySQLGateway.deleteById(GenreID.from(genreId));
        assertEquals(0, genreRepository.count());
    }

    @Test
    public void givenAValidGenreWhenCallsFindByIdShouldReturnGenre() {
        Category category = categoryMySQLGateway.create(Category.newCategory("Filmes", null, true));
        final String expectedName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(category.getId());
        Genre genre = Genre.newGenre(expectedName, isActive);
        genre.addCategories(expectedCategories);
        assertEquals(0, genreRepository.count());
        genreRepository.saveAndFlush(GenreJpaEntity.from(genre));
        assertEquals(1, genreRepository.count());
        final Genre actualGenre = genreMySQLGateway.findById(genre.getId()).get();
        assertEquals(1, genreRepository.count());
        assertEquals(genre.getId(), actualGenre.getId());
        assertEquals(expectedName, actualGenre.getName());
        assertEquals(isActive, actualGenre.isActive());
        assertEquals(actualGenre.getCreatedAt(), genre.getCreatedAt());
        assertEquals(actualGenre.getUpdatedAt(), genre.getUpdatedAt());
        assertEquals(actualGenre.getDeletedAt(), genre.getDeletedAt());
    }

    @Test
    public void givenPrePersistedGenreswhenCallsFindAllshouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;
        List<CategoryID> categories = List.of(
                categoryMySQLGateway.create(Category.newCategory("Filmes", null, true)).getId(),
                categoryMySQLGateway.create(Category.newCategory("Séries", null, true)).getId(),
                categoryMySQLGateway.create(Category.newCategory("Documentários", null, true)).getId()
        );
        categoryMySQLGateway.create(Category.newCategory("Filmes", null, true));
        Genre acao = Genre.newGenre("Ação", true);
        Genre drama = Genre.newGenre("Drama", true);
        Genre suspense = Genre.newGenre("Suspense", true);
        acao.addCategories(categories);
        drama.addCategories(categories);
        suspense.addCategories(categories);
        genreRepository.saveAllAndFlush(List.of(
                GenreJpaEntity.from(acao),
                GenreJpaEntity.from(drama),
                GenreJpaEntity.from(suspense)
        ));
        assertEquals(3, genreRepository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        Pagination<Genre> actualResult = genreMySQLGateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(acao.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenEmptyGenreTablewhenCallsFindAllshouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;
        assertEquals(0, genreRepository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        Pagination<Genre> actualResult = genreMySQLGateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(0, actualResult.items().size());
    }
}
